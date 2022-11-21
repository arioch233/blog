<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-upload
        :action="'/api/file/image/upload'"
        :show-file-list="false"
        :on-success="handleFileUploadSuccess"
        :before-upload="beforeUpload"
        style="margin-right: 15px"
      >
        <el-button type="primary" size="small" icon="el-icon-plus">
          上传
        </el-button>
      </el-upload>

      <el-button
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="this.fileIdList.length === 0"
        @click="isDelete = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left: auto">
        <!-- 格式 -->
        <el-select
          clearable
          size="small"
          v-model="fileSuffix"
          filterable
          placeholder="请选择文件格式"
          style="margin-right: 1rem; width: 180px"
        >
          <el-option
            v-for="item in fileSuffixList"
            :key="item.id"
            :label="item.suffixName"
            :value="item.suffixName"
          />
        </el-select>
        <!-- 类型 -->
        <el-select
          clearable
          size="small"
          v-model="fileType"
          filterable
          placeholder="请选择文件分类"
          style="margin-right: 1rem; width: 180px"
        >
          <el-option
            v-for="item in fileTypeList"
            :key="item.id"
            :label="item.typeName"
            :value="item.typeName"
          />
        </el-select>
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入图片名"
          style="width: 200px"
          @keyup.enter.native="listFiles"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listFiles"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="fileList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column
        type="selection"
        width="55"
        align="center"
      ></el-table-column>
      <el-table-column label="预览" align="center">
        <template slot-scope="scope">
          <el-image
            style="width: 180px; height: 100px"
            :src="scope.row.url"
            :preview-src-list="srcList"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="filename"
        label="文件名称"
        align="center"
        width="300"
      ></el-table-column>
      <el-table-column
        prop="type"
        label="文件类型"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="size"
        label="文件大小(kb)"
        align="center"
      ></el-table-column>
      <el-table-column label="下载" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="download(scope.row.url)"
            >下载</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="success"
            size="small"
            @click="copyEvent(scope.row.url)"
            >复制链接</el-button
          >
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 1rem"
            @confirm="deleteFile(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div style="padding: 10px 0">
      <el-pagination
        class="pagination-container"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 20]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteFile(null)"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import * as imageConversion from "image-conversion";

export default {
  name: "FileView",
  created() {
    this.listFiles();
  },
  data() {
    return {
      loading: true,
      isDelete: false,
      fileUpload: false,
      fileList: [],
      keywords: null,
      fileIdList: [],
      fileSuffix: null,
      fileSuffixList: [
        {
          id: 1,
          suffixName: "jpg",
        },
        {
          id: 2,
          suffixName: "jpeg",
        },
        {
          id: 3,
          suffixName: "png",
        },
        {
          id: 4,
          suffixName: "gif",
        },
      ],
      fileType: null,
      fileTypeList: [
        {
          id: 1,
          typeName: "AVATAR",
        },
        {
          id: 2,
          typeName: "IMAGES",
        },
        {
          id: 3,
          typeName: "ARTICLE",
        },
        {
          id: 4,
          typeName: "PHOTO",
        },
        {
          id: 5,
          typeName: "CONFIG",
        },
      ],
      current: 1,
      size: 10,
      total: 0,
      srcList: [],
    };
  },
  methods: {
    listFiles() {
      this.srcList = [];
      this.request
        .get("/file/all", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords,
            fileType: this.fileType,
            fileSuffix: this.fileSuffix,
          },
        })
        .then((res) => {
          this.generateSrcList(res.data.records);
          this.fileList = res.data.records;
          this.total = res.data.total;
          this.loading = false;
        });
    },
    generateSrcList(json) {
      json.forEach((value) => {
        this.srcList.push(value.url);
      });
    },
    handleSizeChange(pageSize) {
      this.size = pageSize;
      this.listFiles();
    },
    handleCurrentChange(pageNum) {
      this.current = pageNum;
      this.listFiles();
    },
    handleFileUploadSuccess(res) {
      this.$message.success("上传成功");
      this.listFiles();
    },
    beforeUpload(file) {
      return new Promise((resolve) => {
        if (file.size / 1024 < this.config.UPLOAD_SIZE) {
          resolve(file);
        }
        // 压缩到200KB,这里的200就是要压缩的大小,可自定义
        imageConversion
          .compressAccurately(file, this.config.UPLOAD_SIZE)
          .then((res) => {
            resolve(res);
          });
      });
    },
    selectionChange(fileList) {
      this.fileIdList = [];
      fileList.forEach((item) => {
        this.fileIdList.push(item.id);
      });
    },
    download(url) {
      window.open(url);
    },
    copyEvent(url) {
      const textArea = document.createElement("textarea");
      document.body.appendChild(textArea);
      textArea.readOnly = "readonly";
      textArea.style.opacity = "0";
      textArea.value = url;
      textArea.select();
      if (textArea.setSelectionRange)
        textArea.setSelectionRange(0, textArea.value.length);
      else textArea.select();
      document.execCommand("copy");
      this.$notify.success({
        title: "复制成功",
        message: "已复制到剪贴板",
      });
      document.body.removeChild(textArea);
    },
    deleteFile(id) {
      let param = {};
      if (id == null) {
        param = { data: this.fileIdList };
      } else {
        param = { data: [id] };
      }
      this.request.delete("/file/delete", param).then((res) => {
        if (res.code === 200) {
          this.$message.success("删除成功");
          this.listFiles();
          this.isDelete = false;
        } else {
          this.$message.error("删除失败");
        }
      });
    },
  },
  watch: {
    fileSuffix() {
      this.current = 1;
      this.listFiles();
    },
    fileType() {
      this.current = 1;
      this.listFiles();
    },
  },
};
</script>

<style scoped></style>
