<template>
  <div class="flex flex-col mt-3 shadow-md border rounded-md">
    <div class="flex items-center px-3 pt-2">
      <trend-two theme="outline" size="20" fill="#333" />
      <span class="text-lg font-bold ml-2">任务完成量趋势</span>
    </div>
    <div id="chart-container" style="width: 100%;height:350px;"></div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, computed } from 'vue'
import { TrendTwo } from '@icon-park/vue-next'
import { getDateStr } from '@/utils/base'
import * as echarts from 'echarts'
// ******************************************** 计算过去的七天日期--数组
const dayList = computed(() => {
  const days: string[] = [];
  for (let index = 1; index <= 7; index++) {
    days.push(getDateStr(-index));
  }
  return days;
})
/**
 * 加载图表
 */
onMounted(() => {
  const myChart = echarts.init(document.getElementById("chart-container") as HTMLElement);
  myChart.setOption({
    title: {
      text: '显示了过去一周的任务完成量趋势',
      padding: [
        5,  // 上
        13, // 右
        0,  // 下
        10, // 左
      ],
      textStyle: {
        fontSize: 12,
        color: "#64748b"
      }
    },
    tooltip: {},
    xAxis: {
      data: dayList.value
    },
    yAxis: {},
    series: [
      {
        name: '任务完成数',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20, 10],
        itemStyle: {
          borderRadius: [3, 3, 0, 0]
        }
      }
    ]
  });
  // 监听容器大小的改变
  window.onresize = function () {
    myChart.resize();
  };
})
for (let index = 1; index <= 8; index++) {
  console.log(getDateStr(-index));
}

</script>
<style module="styles">
</style>