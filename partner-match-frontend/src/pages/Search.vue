<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from "vue-router";

const router = useRouter();
const origintag = [
  {
    text: '性别',
    children: [
      {text: '男', id: "男"},
      {text: '女', id: "女"},
    ],
  },
  {
    text: '年级',
    children: [
      {text: '大一', id: "大一"},
      {text: '大三', id: "大二"},
      {text: '研一', id: "研一"},
    ],
  },
]

let tagList = ref(origintag);

const searchText = ref('');
const onSearch = (val) => {
  tagList.value = origintag.map(parentTag => {
    const tempChildren = [...parentTag.children]
    const tempparenrtTag = {...parentTag}
    tempparenrtTag.children = tempChildren
        .filter(item => item.text.includes(searchText.value));
    return tempparenrtTag;
  });
}
const onCancel = () => {
  tagList.value = origintag;
};

//已选择标签
const activeIds = ref([]);
const activeIndex = ref(0);


const doClose = (tag) => {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag
  })
}
const doSearchResult = () => {
  router.push({path: '/search/list', query: {tags: activeIds.value}})
}
</script>

<template>
  <form action="/">
    <van-search
        v-model="searchText"
        show-action
        placeholder="请输入搜索关键词"
        @search="onSearch"
        @cancel="onCancel"
    />
  </form>
  <van-divider>已选择标签</van-divider>
  <div v-if="activeIds.length === 0">请选择标签</div>
  <van-row gutter="10">
    <van-col v-for="tag in activeIds">
      <van-tag closeable size="small" type="primary" @close="doClose(tag)">
        {{ tag }}
      </van-tag>
    </van-col>

  </van-row>

  <van-divider>选择标签</van-divider>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
  />
<div style="margin: 12px">
  <van-button block type="primary" @click="doSearchResult">搜索</van-button>
</div>


</template>

<style scoped>

</style>