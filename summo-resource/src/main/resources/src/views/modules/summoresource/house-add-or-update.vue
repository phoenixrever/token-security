<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房间名称" prop="title">
      <el-input v-model="dataForm.title" placeholder="房间名称"></el-input>
    </el-form-item>
    <el-form-item label="租金" prop="price">
      <el-input v-model="dataForm.price" placeholder="租金"></el-input>
    </el-form-item>
    <el-form-item label="礼金" prop="giftPrice">
      <el-input v-model="dataForm.giftPrice" placeholder="礼金"></el-input>
    </el-form-item>
    <el-form-item label="押金" prop="deposit">
      <el-input v-model="dataForm.deposit" placeholder="押金"></el-input>
    </el-form-item>
    <el-form-item label="面积" prop="area">
      <el-input v-model="dataForm.area" placeholder="面积"></el-input>
    </el-form-item>
    <el-form-item label="築年数" prop="age">
      <el-input v-model="dataForm.age" placeholder="築年数"></el-input>
    </el-form-item>
    <el-form-item label="地点" prop="location">
      <el-input v-model="dataForm.location" placeholder="地点"></el-input>
    </el-form-item>
    <el-form-item label="是否可以" prop="isEnable">
      <el-input v-model="dataForm.isEnable" placeholder="是否可以"></el-input>
    </el-form-item>
    <el-form-item label="房间具体规格" prop="detailRoom">
      <el-input v-model="dataForm.detailRoom" placeholder="房间具体规格"></el-input>
    </el-form-item>
    <el-form-item label="建造材料" prop="buildMaterial">
      <el-input v-model="dataForm.buildMaterial" placeholder="建造材料"></el-input>
    </el-form-item>
    <el-form-item label="楼层" prop="floor">
      <el-input v-model="dataForm.floor" placeholder="楼层"></el-input>
    </el-form-item>
    <el-form-item label="建造年份" prop="buildDate">
      <el-input v-model="dataForm.buildDate" placeholder="建造年份"></el-input>
    </el-form-item>
    <el-form-item label="损保" prop="depreciation">
      <el-input v-model="dataForm.depreciation" placeholder="损保"></el-input>
    </el-form-item>
    <el-form-item label="駐車場费用" prop="carPark">
      <el-input v-model="dataForm.carPark" placeholder="駐車場费用"></el-input>
    </el-form-item>
    <el-form-item label="可以入住时间" prop="checkIn">
      <el-input v-model="dataForm.checkIn" placeholder="可以入住时间"></el-input>
    </el-form-item>
    <el-form-item label="総戸数" prop="totalHouse">
      <el-input v-model="dataForm.totalHouse" placeholder="総戸数"></el-input>
    </el-form-item>
    <el-form-item label="情報更新日" prop="houseUpdate">
      <el-input v-model="dataForm.houseUpdate" placeholder="情報更新日"></el-input>
    </el-form-item>
    <el-form-item label="契約期間" prop="duration">
      <el-input v-model="dataForm.duration" placeholder="契約期間"></el-input>
    </el-form-item>
    <el-form-item label="仲介手数料" prop="commission">
      <el-input v-model="dataForm.commission" placeholder="仲介手数料"></el-input>
    </el-form-item>
    <el-form-item label="保証会社" prop="companyPrice">
      <el-input v-model="dataForm.companyPrice" placeholder="保証会社"></el-input>
    </el-form-item>
    <el-form-item label="ほか初期費用" prop="totalPrice">
      <el-input v-model="dataForm.totalPrice" placeholder="ほか初期費用"></el-input>
    </el-form-item>
    <el-form-item label="ほか諸費用" prop="otherPrice">
      <el-input v-model="dataForm.otherPrice" placeholder="ほか諸費用"></el-input>
    </el-form-item>
    <el-form-item label="備考" prop="remarks">
      <el-input v-model="dataForm.remarks" placeholder="備考"></el-input>
    </el-form-item>
    <el-form-item label="经度" prop="mapLongitude">
      <el-input v-model="dataForm.mapLongitude" placeholder="经度"></el-input>
    </el-form-item>
    <el-form-item label="纬度 " prop="mapLatitude">
      <el-input v-model="dataForm.mapLatitude" placeholder="纬度 "></el-input>
    </el-form-item>
    <el-form-item label="房间设施" prop="roomDecoration">
      <el-input v-model="dataForm.roomDecoration" placeholder="房间设施"></el-input>
    </el-form-item>
    <el-form-item label="summo 链接" prop="summoLink">
      <el-input v-model="dataForm.summoLink" placeholder="summo 链接"></el-input>
    </el-form-item>
    <el-form-item label="步行时间" prop="walkTime">
      <el-input v-model="dataForm.walkTime" placeholder="步行时间"></el-input>
    </el-form-item>
    <el-form-item label="管理費・共益費	" prop="managementPrice">
      <el-input v-model="dataForm.managementPrice" placeholder="管理費・共益費	"></el-input>
    </el-form-item>
    <el-form-item label="几K" prop="room">
      <el-input v-model="dataForm.room" placeholder="几K"></el-input>
    </el-form-item>
    <el-form-item label="向き	" prop="direction">
      <el-input v-model="dataForm.direction" placeholder="向き	"></el-input>
    </el-form-item>
    <el-form-item label="建物種別	" prop="classify">
      <el-input v-model="dataForm.classify" placeholder="建物種別	"></el-input>
    </el-form-item>
    <el-form-item label="" prop="vrLink">
      <el-input v-model="dataForm.vrLink" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="vrImage">
      <el-input v-model="dataForm.vrImage" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="条件" prop="requirement">
      <el-input v-model="dataForm.requirement" placeholder="条件"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          title: '',
          price: '',
          giftPrice: '',
          deposit: '',
          area: '',
          age: '',
          location: '',
          isEnable: '',
          detailRoom: '',
          buildMaterial: '',
          floor: '',
          buildDate: '',
          depreciation: '',
          carPark: '',
          checkIn: '',
          totalHouse: '',
          houseUpdate: '',
          duration: '',
          commission: '',
          companyPrice: '',
          totalPrice: '',
          otherPrice: '',
          remarks: '',
          mapLongitude: '',
          mapLatitude: '',
          roomDecoration: '',
          summoLink: '',
          walkTime: '',
          managementPrice: '',
          room: '',
          direction: '',
          classify: '',
          vrLink: '',
          vrImage: '',
          requirement: ''
        },
        dataRule: {
          title: [
            { required: true, message: '房间名称不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '租金不能为空', trigger: 'blur' }
          ],
          giftPrice: [
            { required: true, message: '礼金不能为空', trigger: 'blur' }
          ],
          deposit: [
            { required: true, message: '押金不能为空', trigger: 'blur' }
          ],
          area: [
            { required: true, message: '面积不能为空', trigger: 'blur' }
          ],
          age: [
            { required: true, message: '築年数不能为空', trigger: 'blur' }
          ],
          location: [
            { required: true, message: '地点不能为空', trigger: 'blur' }
          ],
          isEnable: [
            { required: true, message: '是否可以不能为空', trigger: 'blur' }
          ],
          detailRoom: [
            { required: true, message: '房间具体规格不能为空', trigger: 'blur' }
          ],
          buildMaterial: [
            { required: true, message: '建造材料不能为空', trigger: 'blur' }
          ],
          floor: [
            { required: true, message: '楼层不能为空', trigger: 'blur' }
          ],
          buildDate: [
            { required: true, message: '建造年份不能为空', trigger: 'blur' }
          ],
          depreciation: [
            { required: true, message: '损保不能为空', trigger: 'blur' }
          ],
          carPark: [
            { required: true, message: '駐車場费用不能为空', trigger: 'blur' }
          ],
          checkIn: [
            { required: true, message: '可以入住时间不能为空', trigger: 'blur' }
          ],
          totalHouse: [
            { required: true, message: '総戸数不能为空', trigger: 'blur' }
          ],
          houseUpdate: [
            { required: true, message: '情報更新日不能为空', trigger: 'blur' }
          ],
          duration: [
            { required: true, message: '契約期間不能为空', trigger: 'blur' }
          ],
          commission: [
            { required: true, message: '仲介手数料不能为空', trigger: 'blur' }
          ],
          companyPrice: [
            { required: true, message: '保証会社不能为空', trigger: 'blur' }
          ],
          totalPrice: [
            { required: true, message: 'ほか初期費用不能为空', trigger: 'blur' }
          ],
          otherPrice: [
            { required: true, message: 'ほか諸費用不能为空', trigger: 'blur' }
          ],
          remarks: [
            { required: true, message: '備考不能为空', trigger: 'blur' }
          ],
          mapLongitude: [
            { required: true, message: '经度不能为空', trigger: 'blur' }
          ],
          mapLatitude: [
            { required: true, message: '纬度 不能为空', trigger: 'blur' }
          ],
          roomDecoration: [
            { required: true, message: '房间设施不能为空', trigger: 'blur' }
          ],
          summoLink: [
            { required: true, message: 'summo 链接不能为空', trigger: 'blur' }
          ],
          walkTime: [
            { required: true, message: '步行时间不能为空', trigger: 'blur' }
          ],
          managementPrice: [
            { required: true, message: '管理費・共益費	不能为空', trigger: 'blur' }
          ],
          room: [
            { required: true, message: '几K不能为空', trigger: 'blur' }
          ],
          direction: [
            { required: true, message: '向き	不能为空', trigger: 'blur' }
          ],
          classify: [
            { required: true, message: '建物種別	不能为空', trigger: 'blur' }
          ],
          vrLink: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          vrImage: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          requirement: [
            { required: true, message: '条件不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/summoresource/house/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.title = data.house.title
                this.dataForm.price = data.house.price
                this.dataForm.giftPrice = data.house.giftPrice
                this.dataForm.deposit = data.house.deposit
                this.dataForm.area = data.house.area
                this.dataForm.age = data.house.age
                this.dataForm.location = data.house.location
                this.dataForm.isEnable = data.house.isEnable
                this.dataForm.detailRoom = data.house.detailRoom
                this.dataForm.buildMaterial = data.house.buildMaterial
                this.dataForm.floor = data.house.floor
                this.dataForm.buildDate = data.house.buildDate
                this.dataForm.depreciation = data.house.depreciation
                this.dataForm.carPark = data.house.carPark
                this.dataForm.checkIn = data.house.checkIn
                this.dataForm.totalHouse = data.house.totalHouse
                this.dataForm.houseUpdate = data.house.houseUpdate
                this.dataForm.duration = data.house.duration
                this.dataForm.commission = data.house.commission
                this.dataForm.companyPrice = data.house.companyPrice
                this.dataForm.totalPrice = data.house.totalPrice
                this.dataForm.otherPrice = data.house.otherPrice
                this.dataForm.remarks = data.house.remarks
                this.dataForm.mapLongitude = data.house.mapLongitude
                this.dataForm.mapLatitude = data.house.mapLatitude
                this.dataForm.roomDecoration = data.house.roomDecoration
                this.dataForm.summoLink = data.house.summoLink
                this.dataForm.walkTime = data.house.walkTime
                this.dataForm.managementPrice = data.house.managementPrice
                this.dataForm.room = data.house.room
                this.dataForm.direction = data.house.direction
                this.dataForm.classify = data.house.classify
                this.dataForm.vrLink = data.house.vrLink
                this.dataForm.vrImage = data.house.vrImage
                this.dataForm.requirement = data.house.requirement
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/summoresource/house/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'title': this.dataForm.title,
                'price': this.dataForm.price,
                'giftPrice': this.dataForm.giftPrice,
                'deposit': this.dataForm.deposit,
                'area': this.dataForm.area,
                'age': this.dataForm.age,
                'location': this.dataForm.location,
                'isEnable': this.dataForm.isEnable,
                'detailRoom': this.dataForm.detailRoom,
                'buildMaterial': this.dataForm.buildMaterial,
                'floor': this.dataForm.floor,
                'buildDate': this.dataForm.buildDate,
                'depreciation': this.dataForm.depreciation,
                'carPark': this.dataForm.carPark,
                'checkIn': this.dataForm.checkIn,
                'totalHouse': this.dataForm.totalHouse,
                'houseUpdate': this.dataForm.houseUpdate,
                'duration': this.dataForm.duration,
                'commission': this.dataForm.commission,
                'companyPrice': this.dataForm.companyPrice,
                'totalPrice': this.dataForm.totalPrice,
                'otherPrice': this.dataForm.otherPrice,
                'remarks': this.dataForm.remarks,
                'mapLongitude': this.dataForm.mapLongitude,
                'mapLatitude': this.dataForm.mapLatitude,
                'roomDecoration': this.dataForm.roomDecoration,
                'summoLink': this.dataForm.summoLink,
                'walkTime': this.dataForm.walkTime,
                'managementPrice': this.dataForm.managementPrice,
                'room': this.dataForm.room,
                'direction': this.dataForm.direction,
                'classify': this.dataForm.classify,
                'vrLink': this.dataForm.vrLink,
                'vrImage': this.dataForm.vrImage,
                'requirement': this.dataForm.requirement
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
