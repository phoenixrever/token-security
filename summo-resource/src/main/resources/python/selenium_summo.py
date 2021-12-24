# coding: utf-8
import time

import pymysql
import urllib.parse
from lxml import etree
from selenium import webdriver
from selenium.webdriver.chrome.service import Service


from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By

options = webdriver.ChromeOptions()
# 不打开浏览器
options.add_argument('--headless')
options.add_argument("--disable-gpu")
# options.add_argument('--blink-settings=imagesEnabled=false')  # 不加载图片, 提升速度
# 伪造请求 防止selenium 被监测到
options.add_experimental_option('excludeSwitches', ['enable-automation'])  # 防止网站识别Selenium代码

browser = webdriver.Chrome(service=Service("../chromedriver_office.exe"), options=options)

browser.implicitly_wait(3)

# WebDriverWait(browser, 3).until(EC.presence_of_element_located((By.CLASS_NAME, 'section_h1-header-title')))
# WebDriverWait(browser, 3).until_not(EC.presence_of_element_located((By.ID, 'wrapper1')))


def my_request(link):
    browser.get(link)
    time.sleep(1)
    page_text = browser.page_source
    tree = etree.HTML(page_text)
    return tree


def getData(param):
    print(param)
    tree = my_request(param)
    groups = tree.xpath('//div[@class="property_group"]')
    for item in groups:
        try:
            # .// 表示当前节点下的元素
            # names = item.xpath('.//*[@class="property_inner-title"]/a/text()')
            hrefs = item.xpath('.//a[@class="js-cassetLinkHref"]/@href')
            # print(hrefs)
            # 详情页发送请求
            for i in range(0, len(hrefs)):
                time.sleep(2)
                url_detail = 'https://suumo.jp' + hrefs[i]
                print(url_detail)
                summo_link=url_detail
                detail_tree = my_request(url_detail)
                title = detail_tree.xpath('//*[@class="section_h1-header-title"]/text()')
                if len(title) > 0:
                    title = title[0].strip()
                # 是否是重复数据
                select_sql = "select * from house where title = '%s'" % (title)
                # print(select_sql)
                cursor.execute(select_sql)
                results = cursor.fetchall()
                if len(results) > 0:
                    exit(0)
                    # continue

                price = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div/div[1]/text()')[
                    0].strip()
                # print(price)
                management_price = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div/div[2]/div/div[2]/text()')[
                    0].strip()
                gift_price = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[2]/div/div[2]/div/div[2]/ul/li[1]/div/div[2]/span[3]/text()')[
                    0].strip()
                deposit = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[2]/div/div[2]/div/div[2]/ul/li[2]/div/div[2]/text()')[
                    0].strip()
                room = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[1]/div/div[2]/ul/li[1]/div/div[2]/text()')[
                    0].strip()
                area = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[1]/div/div[2]/ul/li[2]/div/div[2]/text()')[
                    0].strip()
                direction = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[1]/div/div[2]/ul/li[3]/div/div[2]/text()')[
                    0].strip()
                classify = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[1]/div/div[2]/ul/li[4]/div/div[2]/text()')[
                    0].strip()
                age = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[1]/div/div[2]/ul/li[5]/div/div[2]/text()')[
                    0].strip()
                access = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[2]/div[1]/div/div[2]//text()')
                access_list = []
                for a in access:
                    if a.strip() != '':
                        access_list.append(a.strip())
                walk_time = ','.join(access_list)
                location = detail_tree.xpath(
                    '//*[@id="js-view_gallery"]/div[1]/div[2]/div[3]/div[2]/div[2]/div/div[2]/div/text()')[0].strip()
                # print('walk_time', walk_time)

                # 部屋の特徴・設備 room_decoration
                room_decoration = detail_tree.xpath('//*[@id="bkdt-option"]/div/ul/li/text()')[0].strip()
                # 物件概要
                detail_room = ''
                build_material = ''
                build_date = ''
                car_park = ''
                floor = ''
                depreciation = ''
                check_in = ''
                requirement = ''
                total_house = ''
                house_update = ''
                duration = ''
                commission = ''
                company_price = ''
                total_price = ''
                other_price = ''
                remarks = ''
                tables = detail_tree.xpath('//*[@id="contents"]//table//tr')
                for index, tr in enumerate(tables):
                    if index > 6:
                        name = tr.xpath('.//th/text()')
                        if len(name) != 0 and name[0].strip() == '契約期間':
                            duration = tr.xpath('.//td/ul/li/text()')[0].strip()
                        if len(name) != 0 and name[0].strip() == '仲介手数料':
                            commission = tr.xpath('.//td/ul/li/text()')[0].strip()
                        if len(name) != 0 and name[0].strip() == '保証会社':
                            company_price = tr.xpath('.//td/ul/li/text()')[0].strip()
                        if len(name) != 0 and name[0].strip() == 'ほか初期費用':
                            total_price = tr.xpath('.//td/ul/li/text()')[0].strip()
                        if len(name) != 0 and name[0].strip() == 'ほか諸費用':
                            other_price = tr.xpath('.//td/ul/li/text()')[0].strip()
                        if len(name) != 0 and name[0].strip() == '備考':
                            remarks = tr.xpath('.//td/ul/li/text()')[0].strip()
                    else:
                        if index == 0:
                            detail_room = tr.xpath('.//td[1]/text()')[0].strip()
                            build_material = tr.xpath('.//td[2]/text()')[
                                0].strip()
                        if index == 1:
                            floor = tr.xpath('.//td[1]/text()')[0].strip()
                            build_date = tr.xpath('.//td[2]/text()')[
                                0].strip()
                        if index == 2:
                            depreciation = tr.xpath('.//td[1]/text()')[
                                0].strip()
                            car_park = tr.xpath('.//td[2]/text()')[
                                0].strip()
                        if index == 3:
                            check_in = tr.xpath('.//td[1]/text()')[
                                0].strip("'").strip()
                        if index == 4:
                            requirement = tr.xpath('.//td[1]/text()')[
                                0].strip().strip('')
                        if index == 5:
                            total_house = tr.xpath('.//td[2]/text()')[
                                0].strip()
                        if index == 6:
                            house_update = tr.xpath('.//td[1]/text()')[
                                0].strip()

                # vr预览图的 有的页面是没有
                iframe = detail_tree.xpath('//*[@class="panoramabox"]/iframe/@src')
                vr_image = ''
                vr_link = ''
                if len(iframe) != 0:
                    # print(iframe[0].strip())
                    vr_link = iframe[0].strip()
                # 数据库插入数据
                house_sql = "insert into house(title, price, management_price, gift_price, deposit, room, area," \
                            "direction,classify, age, walk_time, location, vr_image, vr_link, room_decoration," \
                            "detail_room, build_material, floor, build_date, depreciation, car_park, check_in," \
                            "requirement,total_house, house_update,duration, commission, company_price, " \
                            "total_price,other_price, remarks,summo_link)" \
                            " values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')" % (
                                title, price, management_price, gift_price, deposit, room, area, direction, classify,
                                age, walk_time,
                                location, vr_image, vr_link, room_decoration, detail_room, build_material, floor,
                                build_date,
                                depreciation, car_park, check_in, requirement, total_house, house_update, duration,
                                commission,
                                company_price, total_price, other_price, remarks,summo_link)
                print(house_sql)
                cursor.execute(house_sql)

                house_id = cursor.lastrowid
                db.commit()

                # ============所有图片 单独的表====================
                # 所有图片
                house_images = []
                images = detail_tree.xpath('//*[@id="js-view_gallery-list"]//img/@data-src')
                image_sql = 'insert into image values'
                for image in images:
                    house_images.append(image.strip())
                    image_sql = image_sql + '(' + 'null,' + "'" + image.strip() + "'" + ",'" + str(house_id) + "'),"
                    print(image_sql)
                print(house_images)
                print(image_sql.strip(','))
                cursor.execute(image_sql.strip(','))
                db.commit()

                # vr封面
                vr_image = house_images[0]
                # =============================================

                # =================  周辺環境表 里面有地图 经纬度===================
                nearUrl = detail_tree.xpath('//*[@class="data_around"]//a/@href')[0].strip()
                print('https://suumo.jp' + nearUrl)
                nearTree = my_request('https://suumo.jp' + nearUrl)

                # 地图
                map_longitude = ''
                map_latitude = ''
                action = nearTree.xpath('//*[@id="js-timesForm"]/@action')
                if len(action) > 0:
                    o = urllib.parse.urlparse(action[0].strip())
                    locations = urllib.parse.parse_qs(o.query)
                    print(locations)
                    map_longitude = locations.get('ido')[0]
                    map_latitude = locations.get('keido')[0]

                # 周边
                dls = nearTree.xpath('//*[@id="js-kankyo_photo_list"]//dl')
                if len(dls) > 0:
                    near_sql = 'insert into neighborhood values'
                    for dl in dls:
                        dl_img = dl.xpath('.//dt/img/@src')[0].strip()
                        dl_title = dl.xpath('.//dd/text()')[0].strip()
                        print(dl_img)
                        print(dl_title)
                        near_sql = near_sql + '(' + 'null,' + "'" + dl_img + "'" + ',' + "'" + dl_title + "'" + ',' + "'" + str(
                            house_id) + "'" + '),'
                    print(near_sql.strip(','))
                    cursor.execute(near_sql.strip(','))
                    db.commit()

                # 插入house 表
                cursor.execute("update house set vr_image='%s',map_longitude='%s',map_latitude='%s' where id=%d" % (
                    vr_image, map_longitude, map_latitude, house_id))
                db.commit()
                # print(title, price, management_price, gift_price, deposit, room, area, direction,
                #       classify, age, walk_time, location, vr_image, vr_link, room_decoration,
                #       detail_room, build_material, floor, build_date, depreciation, car_park, check_in,requirement,
                #       total_house, house_update, duration, commission, company_price, total_price,
                #       other_price, remarks, map_longitude, map_latitude,summo_link)
        except Exception as e:
            print(e.with_traceback())
            pass
        continue


if __name__ == '__main__':
    # db = pymysql.connect(host='192.168.56.100', user='root', password='root', database='summo')
    db = pymysql.connect(host='localhost', user='root', password='159629zxc', database='summo')
    cursor = db.cursor()
    url = 'https://suumo.jp/jj/chintai/ichiran/FR301FC005/?ar=030&bs=040&ra=013&rn=0065&ek=006534520&cb=0.0&ct=5.0&mb=0&mt=9999999&et=9999999&cn=9999999&shkr1=03&shkr2=03&shkr3=03&shkr4=03&sngz=&po1=25&po2=99&pc=10&page='

    params = {
    }
    page = 8
    while page < 100:
        getData(url + (str(page)))
        page += 1
