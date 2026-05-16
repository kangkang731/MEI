<template>
  <div class="mei-system-container">
    <!-- 头部Banner（保持不变） -->
    <div class="header-banner">
      <div class="banner-text">
        <h1 class="banner-title">MEI 双创管理系统</h1>
        <p class="banner-desc">Mass Entrepreneurship and Innovation</p>
      </div>
      <div class="banner-img">
        <img src="https://picsum.photos/id/1015/800/500" alt="科技雪山" class="tech-mountain-img">
      </div>
      <div class="tech-line top"></div>
      <div class="tech-line left"></div>
      <div class="tech-line right"></div>
      <div class="glow-pulse"></div>
    </div>

    <!-- 第一行：系统简介 + 数据统计（轮播图表） -->
    <div class="row">
      <!-- 卡片1：系统简介（四周脉冲 + 机甲科技背景） -->
      <div class="card pulse-card">
        <div class="card-header">
          <span class="icon">📄</span> 系统简介
        </div>
        <div class="card-body">
          <p>MEI双创管理系统是面向“大众创业、万众创新”打造的一体化数字化管理平台，采用<strong class="highlight">“三层一体”</strong>架构设计，整合基础支撑、核心服务、展示应用三大模块，为创新创业全流程提供标准化、高效化的管理服务。</p>
          <p>系统整合项目申报、审核、跟踪、孵化、成果管理等核心环节，对接国家双创平台，实现政策同步、项目直报、成果联网、融资打通，大幅提升双创工作管理效率。</p>
        </div>
        <div class="bg-illustration mecha-circuit-bg"></div>
      </div>

      <!-- 卡片2：项目数据统计（轮播饼图/柱状图/折线图） -->
      <div class="card pulse-card">
        <div class="card-header">
          <span class="icon">📊</span> 项目数据统计
          <div class="chart-switch">
            <span class="chart-badge">{{ chartTypeLabel }}</span>
            <button class="refresh-btn" @click="switchChart">⟳ 切换</button>
          </div>
        </div>
        <div id="statsChart" class="stats-chart"></div>
        <div class="bg-illustration mecha-dots-bg"></div>
      </div>
    </div>

    <!-- 第二行：系统核心作用（左） + 三层一体架构图（右） -->
    <div class="row">
      <!-- 卡片3：系统核心作用（四周脉冲 + 机甲网格背景） -->
      <div class="card pulse-card">
        <div class="card-header">
          <span class="icon">⚡</span> 系统核心作用
        </div>
        <div class="feature-list">
          <div class="feature-item"><span class="icon"></span> 统一管理创新创业项目，全流程线上化</div>
          <div class="feature-item"><span class="icon"></span> 数据统计、项目监管、科学决策</div>
          <div class="feature-item"><span class="icon"></span> 便捷申报、资源对接、加速落地</div>
          <div class="feature-item"><span class="icon"></span> 信息透明化，推动科技成果转化</div>
          <div class="feature-item"><span class="icon"></span> 流程规范化，降低管理成本</div>
          <div class="feature-item"><span class="icon"></span> 藏汉双语适配、创新券申领、高校实验室预约</div>
        </div>
        <div class="bg-illustration mecha-grid-bg"></div>
      </div>

      <!-- 卡片4：双创管理系统业务流程（方案一风格 + 透明渐变 + 机甲装饰） -->
      <div class="card pulse-card">
        <div class="card-header">
          <span class="icon">🏛️</span> 双创管理系统业务流程（三层一体架构）
        </div>
        <!-- 新增渐变背景层容器 -->
        <div class="flow-chart-wrapper">
          <div id="flowChart" class="flow-echarts-light"></div>
          <!-- 三层渐变背景色块（透明渐变） -->
          <div class="layer-bg layer-bg-1"></div>
          <div class="layer-bg layer-bg-2"></div>
          <div class="layer-bg layer-bg-3"></div>
        </div>
        <div class="bg-illustration mecha-network-bg"></div>
        <!-- 保留机甲装饰元素 -->
        <div class="mecha-corner top-left"></div>
        <div class="mecha-corner top-right"></div>
        <div class="mecha-corner bottom-left"></div>
        <div class="mecha-corner bottom-right"></div>
        <div class="mecha-grid-overlay"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Dashboard",
  data() {
    return {
      statsChart: null,
      flowChart: null,
      // 轮播图表相关
      chartTypes: ['bar', 'pie', 'line'],
      currentChartIndex: 0,
      chartTimer: null,
      // 假数据（后期可替换）
      barData: [1850, 1620, 1780, 2100, 2450],
      barCategories: ['2025年', '2026年', '2027年', '2028年', '2029年'],
      pieData: [
        { name: '政策服务', value: 34 },
        { name: '项目申报', value: 28 },
        { name: '融资服务', value: 18 },
        { name: '成果管理', value: 12 },
        { name: '人才服务', value: 8 }
      ],
      lineData: [120, 145, 178, 210, 268, 310, 355, 402, 468, 512, 578, 620],
      lineMonths: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    }
  },
  computed: {
    chartTypeLabel() {
      const map = { bar: '柱状图', pie: '饼图', line: '折线图' }
      return map[this.chartTypes[this.currentChartIndex]]
    }
  },
  mounted() {
    this.initStatsChart()
    this.initArchitectureChartLight()
    // 自动轮播（每5秒切换）
    this.chartTimer = setInterval(() => {
      this.switchChart()
    }, 5000)
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    clearInterval(this.chartTimer)
    window.removeEventListener('resize', this.handleResize)
    if (this.statsChart) this.statsChart.dispose()
    if (this.flowChart) this.flowChart.dispose()
  },
  methods: {
    handleResize() {
      this.statsChart?.resize()
      this.flowChart?.resize()
    },
    switchChart() {
      this.currentChartIndex = (this.currentChartIndex + 1) % this.chartTypes.length
      this.renderCurrentChart()
    },
    renderCurrentChart() {
      const type = this.chartTypes[this.currentChartIndex]
      if (type === 'bar') this.renderBarChart()
      else if (type === 'pie') this.renderPieChart()
      else if (type === 'line') this.renderLineChart()
    },
    initStatsChart() {
      const dom = document.getElementById('statsChart')
      if (!dom) return
      this.statsChart = echarts.init(dom)
      this.renderCurrentChart()
    },
    renderBarChart() {
      this.statsChart.setOption({
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' }, backgroundColor: 'rgba(10,20,40,0.9)', borderColor: '#66a3ff' },
        grid: { top: 40, left: 50, right: 20, bottom: 30 },
        xAxis: { type: 'category', data: this.barCategories, axisLabel: { color: '#a0b4d8', fontWeight: 'bold' } },
        yAxis: { type: 'value', name: '项目数量', nameTextStyle: { color: '#a0b4d8' }, axisLabel: { color: '#a0b4d8' }, splitLine: { lineStyle: { color: 'rgba(100,160,255,0.2)' } } },
        series: [{
          name: '立项项目', type: 'bar', data: this.barData,
          itemStyle: {
            borderRadius: [8,8,0,0],
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#6ea8fe' }, { offset: 1, color: '#2c6eff' }
            ]),
            shadowColor: '#2c6eff', shadowBlur: 12
          },
          label: { show: true, position: 'top', color: '#ffffff', fontWeight: 'bold', formatter: '{c}' },
          animationDuration: 800
        }]
      })
    },
    renderPieChart() {
      this.statsChart.setOption({
        tooltip: { trigger: 'item', backgroundColor: 'rgba(10,20,40,0.9)', borderColor: '#66a3ff' },
        series: [{
          name: '服务占比', type: 'pie', radius: '55%', center: ['50%', '50%'],
          data: this.pieData,
          label: { show: true, formatter: '{b}: {d}%', color: '#e0eaff', fontWeight: 'bold' },
          emphasis: { scale: true },
          itemStyle: {
            borderRadius: 8,
            borderColor: '#0a0f20',
            borderWidth: 2,
            shadowBlur: 10,
            shadowColor: 'rgba(100,160,255,0.5)'
          }
        }]
      })
    },
    renderLineChart() {
      this.statsChart.setOption({
        tooltip: { trigger: 'axis', backgroundColor: 'rgba(10,20,40,0.9)', borderColor: '#66a3ff' },
        grid: { top: 40, left: 50, right: 20, bottom: 30 },
        xAxis: { type: 'category', data: this.lineMonths, axisLabel: { rotate: 30, color: '#a0b4d8' } },
        yAxis: { type: 'value', name: '活跃企业数', nameTextStyle: { color: '#a0b4d8' }, axisLabel: { color: '#a0b4d8' }, splitLine: { lineStyle: { color: 'rgba(100,160,255,0.2)' } } },
        series: [{
          name: '活跃企业', type: 'line', data: this.lineData,
          smooth: true, symbol: 'circle', symbolSize: 8,
          lineStyle: { width: 3, color: '#6ea8fe', shadowBlur: 12, shadowColor: '#2c6eff' },
          areaStyle: { opacity: 0.2, color: new echarts.graphic.LinearGradient(0,0,0,1, [
              { offset: 0, color: '#6ea8fe' }, { offset: 1, color: '#2c6eff' }
            ]) },
          itemStyle: { color: '#ffffff', borderColor: '#2c6eff', borderWidth: 2 },
          label: { show: true, position: 'top', color: '#ffffff', fontWeight: 'bold' }
        }]
      })
    },
    // ========== 方案一风格的架构图（透明渐变） ==========
    initArchitectureChartLight() {
      const dom = document.getElementById('flowChart')
      if (!dom) return
      if (this.flowChart) this.flowChart.dispose()
      this.flowChart = echarts.init(dom)

      // 渐变色彩定义（适配深色背景的透明渐变）
      const gradients = [
        ['rgba(79, 70, 229, 0.2)', 'rgba(129, 140, 248, 0.05)'], // 展示层 - 透明渐变
        ['rgba(139, 92, 246, 0.2)', 'rgba(167, 139, 250, 0.05)'], // 核心层 - 透明渐变
        ['rgba(79, 70, 229, 0.2)', 'rgba(129, 140, 248, 0.05)']  // 基础层 - 透明渐变
      ];

      const layers = [
        {
          name: '展示应用层',
          gradient: gradients[0],
          nodes: ['PC门户', '管理后台', '移动端']
        },
        {
          name: '核心服务层',
          gradient: gradients[1],
          nodes: ['政策服务', '项目申报', '融资服务', '成果管理', '人才服务', '咨询服务', '培训服务', '评测服务']
        },
        {
          name: '基础支撑层',
          gradient: gradients[2],
          nodes: ['统一用户', '统一认证', '统一权限', '统一门户', '统一消息']
        }
      ];

      // 布局参数（方案一风格）
      const containerWidth = dom.clientWidth || 1000;
      const containerHeight = 500;
      const layerGapX = 280; // 横向间距调整
      const startX = 80;
      const centerY = containerHeight / 2;
      const nodeWidth = 130;
      const nodeHeight = 50;
      const nodeGapY = 70;

      let allNodes = [];
      let nodeId = 0;

      // 生成节点（方案一透明渐变风格）
      layers.forEach((layer, layerIdx) => {
        const x = startX + layerIdx * layerGapX;
        const nodeCount = layer.nodes.length;
        const startY = centerY - (nodeCount - 1) * nodeGapY / 2;

        layer.nodes.forEach((nodeName, idx) => {
          const y = startY + idx * nodeGapY;
          allNodes.push({
            id: nodeId++,
            name: nodeName,
            x, y,
            symbol: 'roundRect',
            symbolSize: [nodeWidth, nodeHeight],
            category: layerIdx,
            label: {
              show: true,
              position: 'inside',
              fontSize: 14,
              color: '#ffffff',
              fontWeight: '600'
            },
            itemStyle: {
              borderRadius: 16, // 方案一的大圆角
              borderWidth: 0,
              shadowBlur: 15,
              shadowColor: `rgba(${this.hexToRgb(this.rgbaToHex(layer.gradient[0])).r}, ${this.hexToRgb(this.rgbaToHex(layer.gradient[0])).g}, ${this.hexToRgb(this.rgbaToHex(layer.gradient[0])).b}, 0.2)`,
              shadowOffsetY: 8,
              color: {
                type: 'linear',
                x: 0, y: 0, x2: 1, y2: 1,
                colorStops: [
                  { offset: 0, color: layer.gradient[0] },
                  { offset: 1, color: layer.gradient[1] }
                ]
              }
            },
            emphasis: {
              scale: 1.05,
              label: { color: '#ffffff', fontWeight: 'bold', fontSize: 15 },
              itemStyle: {
                shadowBlur: 25,
                shadowColor: `rgba(${this.hexToRgb(this.rgbaToHex(layer.gradient[0])).r}, ${this.hexToRgb(this.rgbaToHex(layer.gradient[0])).g}, ${this.hexToRgb(this.rgbaToHex(layer.gradient[0])).b}, 0.4)`,
                shadowOffsetY: 12
              }
            }
          });
        });
      });

      // 生成连线（方案一柔和渐变线条）
      const displayIds = allNodes.filter(n => n.category === 0).map(n => n.id);
      const coreIds = allNodes.filter(n => n.category === 1).map(n => n.id);
      const baseIds = allNodes.filter(n => n.category === 2).map(n => n.id);

      const edges = [];
      // 展示层 → 核心层
      displayIds.forEach((dId, idx) => {
        const coreSlice = coreIds.slice(idx * 2, idx * 2 + 2);
        coreSlice.forEach(cId => {
          edges.push({
            source: dId, target: cId,
            lineStyle: {
              color: '#94a3b8',
              width: 2,
              curveness: 0.08,
              type: 'solid',
              opacity: 0.6
            },
            emphasis: {
              lineStyle: {
                color: '#4f46e5',
                width: 3,
                opacity: 1
              }
            }
          });
        });
      });

      // 核心层 → 基础层
      coreIds.forEach((cId, idx) => {
        const baseIdx = idx % baseIds.length;
        edges.push({
          source: cId, target: baseIds[baseIdx],
          lineStyle: {
            color: '#94a3b8',
            width: 2,
            curveness: 0.08,
            opacity: 0.6
          },
          emphasis: {
            lineStyle: {
              color: '#8b5cf6',
              width: 3,
              opacity: 1
            }
          }
        });
      });

      // 图表配置（方案一风格）
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}',
          backgroundColor: '#ffffff',
          borderColor: '#e2e8f0',
          borderWidth: 1,
          borderRadius: 12,
          textStyle: { color: '#1e293b', fontSize: 13 },
          padding: [12, 16],
          shadowBlur: 10,
          shadowColor: 'rgba(0,0,0,0.05)'
        },
        grid: { left: 0, right: 0, top: 0, bottom: 0 },
        xAxis: { show: false, min: 0, max: containerWidth },
        yAxis: { show: false, min: 0, max: containerHeight },
        series: [{
          type: 'graph',
          layout: 'none',
          roam: false,
          draggable: false,
          data: allNodes,
          links: edges,
          edgeSymbol: ['none', 'arrow'],
          edgeSymbolSize: [0, 8],
          label: { show: false },
          emphasis: { focus: 'adjacency' }
        }]
      };

      this.flowChart.setOption(option);

      // 响应式更新
      const updateLayout = () => {
        const width = dom.clientWidth;
        const height = dom.clientHeight || 500;
        const newCenterY = height / 2;

        const updatedNodes = allNodes.map((node, idx) => {
          const layerIdx = node.category;
          const layer = layers[layerIdx];
          const nodeIndex = layer.nodes.findIndex(n => n === node.name);
          const nodeCount = layer.nodes.length;
          const newStartY = newCenterY - (nodeCount - 1) * nodeGapY / 2;
          const newX = startX + layerIdx * (width > 768 ? layerGapX : 200);
          const newY = newStartY + nodeIndex * nodeGapY;

          return { ...node, x: newX, y: newY };
        });

        this.flowChart.setOption({
          xAxis: { max: width },
          yAxis: { max: height },
          series: [{ data: updatedNodes }]
        });
      };

      window.addEventListener('resize', updateLayout);
      setTimeout(updateLayout, 100);
    },
    // 辅助函数：16进制转RGB
    hexToRgb(hex) {
      const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : { r: 79, g: 70, b: 229 };
    },
    // 辅助函数：RGBA转16进制（用于阴影计算）
    rgbaToHex(rgba) {
      const rgbaRegex = /rgba\((\d+),\s*(\d+),\s*(\d+),\s*[\d.]+\)/;
      const matches = rgba.match(rgbaRegex);
      if (!matches) return '#4f46e5';

      const r = parseInt(matches[1], 10);
      const g = parseInt(matches[2], 10);
      const b = parseInt(matches[3], 10);

      return `#${((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1)}`;
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Microsoft YaHei", "PingFang SC", "Inter", sans-serif;
}

/* 主背景：机甲科技风深色渐变 */
.mei-system-container {
  width: 95%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 24px 16px;
  background: radial-gradient(circle at 10% 20%, #080F20, #050A15);
  color: #e6edf7;
  line-height: 1.6;
  min-height: 100vh;
  position: relative;
  z-index: 0;
  /* 全局机甲网格底纹 */
  background-image:
    linear-gradient(rgba(22, 93, 255, 0.03) 1px, transparent 1px),
    linear-gradient(90deg, rgba(22, 93, 255, 0.03) 1px, transparent 1px);
  background-size: 40px 40px;
}

/* ========== 卡片通用样式 + 四周脉冲特效 ========== */
.card {
  position: relative;
  background: rgba(8, 15, 32, 0.85);
  backdrop-filter: blur(12px);
  border-radius: 16px; /* 机甲风更硬朗的圆角 */
  padding: 24px;
  transition: all 0.3s ease;
  overflow: hidden;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.4);
  z-index: 1;
  border: 1.5px solid rgba(22, 93, 255, 0.3); /* 统一机甲蓝边框 */
}

/* 四周脉冲动画（机甲蓝呼吸光晕） */
.pulse-card {
  animation: borderPulse 4s infinite ease-in-out;
}

@keyframes borderPulse {
  0% {
    box-shadow: 0 0 0 0 rgba(22, 93, 255, 0.2), 0 8px 20px rgba(0, 0, 0, 0.4);
    border-color: rgba(22, 93, 255, 0.3);
  }
  50% {
    box-shadow: 0 0 0 6px rgba(22, 93, 255, 0.3), 0 12px 30px rgba(22, 93, 255, 0.15);
    border-color: rgba(22, 93, 255, 0.6);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(22, 93, 255, 0.2), 0 8px 20px rgba(0, 0, 0, 0.4);
    border-color: rgba(22, 93, 255, 0.3);
  }
}

.card:hover {
  transform: translateY(-4px);
  animation: none;
  box-shadow: 0 20px 35px rgba(22, 93, 255, 0.2);
  border-color: #4080FF;
}

/* ========== 机甲科技风背景图 ========== */
.bg-illustration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 0;
  opacity: 0.15;
}

/* 机甲电路背景 */
.mecha-circuit-bg {
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 800 600'%3E%3Cpath fill='none' stroke='%23165DFF' stroke-width='1.5' stroke-linecap='square' d='M100 150 L200 100 L300 150 L400 120 L500 160 M50 250 L150 280 L250 220 L350 260 M600 400 L700 380 L750 420 M120 500 L220 470 L320 520'/%3E%3Crect x='180' y='180' width='20' height='20' fill='%23165DFF'/%3E%3Crect x='480' y='280' width='15' height='15' fill='%23165DFF'/%3E%3Crect x='650' y='380' width='10' height='10' fill='%23165DFF'/%3E%3C/svg%3E");
  background-repeat: repeat;
  background-size: 200px;
}

/* 机甲点阵背景 */
.mecha-dots-bg {
  background-image:
    radial-gradient(circle at 20% 30%, rgba(22,93,255,0.5) 1px, transparent 1px),
    radial-gradient(circle at 80% 70%, rgba(255,125,0,0.3) 1px, transparent 1px);
  background-size: 25px 25px;
}

/* 机甲网格背景 */
.mecha-grid-bg {
  background-image:
    linear-gradient(to right, rgba(22,93,255,0.3) 1px, transparent 1px),
    linear-gradient(to bottom, rgba(22,93,255,0.3) 1px, transparent 1px),
    linear-gradient(45deg, rgba(22,93,255,0.1) 1px, transparent 1px);
  background-size: 30px 30px, 30px 30px, 60px 60px;
}

/* 机甲网络背景（适配深色） */
.mecha-network-bg {
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 300'%3E%3Cpath fill='none' stroke='%23165DFF' stroke-width='0.8' stroke-linecap='square' d='M50 50 L120 80 L200 40 L280 70 L350 50 M80 150 L160 180 L240 140 L320 170 M60 250 L140 220 L220 260 L300 230'/%3E%3Crect x='100' y='100' width='8' height='8' fill='%23165DFF'/%3E%3Crect x='200' y='150' width='6' height='6' fill='%23165DFF'/%3E%3Crect x='300' y='200' width='4' height='4' fill='%23165DFF'/%3E%3C/svg%3E");
  background-repeat: repeat;
  background-size: 150px;
  opacity: 0.1;
}

/* ========== 机甲装饰元素 ========== */
/* 机甲边角装饰 */
.mecha-corner {
  position: absolute;
  width: 30px;
  height: 30px;
  border: 2px solid #165DFF;
  z-index: 1;
  opacity: 0.8;
}
.mecha-corner.top-left {
  top: 10px;
  left: 10px;
  border-right: none;
  border-bottom: none;
}
.mecha-corner.top-right {
  top: 10px;
  right: 10px;
  border-left: none;
  border-bottom: none;
}
.mecha-corner.bottom-left {
  bottom: 10px;
  left: 10px;
  border-right: none;
  border-top: none;
}
.mecha-corner.bottom-right {
  bottom: 10px;
  right: 10px;
  border-left: none;
  border-top: none;
}

/* 机甲网格叠加层 */
.mecha-grid-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1;
  opacity: 0.05;
  background-image:
    linear-gradient(to right, #FF7D00 1px, transparent 1px),
    linear-gradient(to bottom, #FF7D00 1px, transparent 1px);
  background-size: 50px 50px;
}

/* 卡片头部 */
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  font-size: 1.3rem;
  font-weight: 600;
  color: #c6e0ff;
  border-left: 4px solid #165DFF; /* 统一机甲蓝 */
  padding-left: 14px;
  position: relative;
  z-index: 2;
}
.card-header .icon { font-size: 1.5rem; margin-right: 8px; }
.card-body { position: relative; z-index: 2; }

/* 图表切换按钮区域 */
.chart-switch {
  display: flex;
  align-items: center;
  gap: 12px;
}
.chart-badge {
  background: rgba(22, 93, 255, 0.2);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.8rem;
  color: #aac8ff;
}
.refresh-btn {
  background: none;
  border: 1px solid #165DFF; /* 统一机甲蓝 */
  color: #165DFF;
  border-radius: 20px;
  padding: 4px 12px;
  cursor: pointer;
  transition: 0.2s;
  font-size: 0.8rem;
}
.refresh-btn:hover {
  background: #165DFF;
  color: #ffffff;
  box-shadow: 0 0 10px rgba(22, 93, 255, 0.5);
}

/* 高亮文字 */
.highlight { color: #4080FF; font-weight: 600; }

/* 核心功能列表 */
.feature-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  position: relative;
  z-index: 2;
}
.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 16px;
  background: rgba(15, 30, 60, 0.5);
  border-radius: 8px; /* 机甲风硬朗圆角 */
  border-left: 3px solid #165DFF; /* 统一机甲蓝 */
  font-size: 14px;
  transition: 0.2s;
  backdrop-filter: blur(4px);
}
.feature-item:hover {
  background: rgba(22, 93, 255, 0.2);
  transform: translateX(6px);
  box-shadow: 0 0 8px rgba(22, 93, 255, 0.3);
}
.feature-item .icon { font-size: 18px; }

/* ECharts 容器 */
.stats-chart { width: 100%; height: 280px; position: relative; z-index: 2; }
.flow-echarts-light { width: 100%; height: 500px; position: relative; z-index: 1; }

/* ========== 方案一风格的渐变背景层 ========== */
.flow-chart-wrapper {
  position: relative;
  width: 100%;
  height: 500px;
  z-index: 1;
}
/* 层级背景色块（透明渐变） */
.layer-bg {
  position: absolute;
  height: 100%;
  width: 300px;
  border-radius: 16px;
  opacity: 0.15; /* 透明效果 */
  z-index: 0;
}
.layer-bg-1 {
  left: 50px;
  background: linear-gradient(90deg, rgba(79, 70, 229, 0.3), rgba(129, 140, 248, 0.05));
}
.layer-bg-2 {
  left: 350px;
  background: linear-gradient(90deg, rgba(139, 92, 246, 0.3), rgba(167, 139, 250, 0.05));
}
.layer-bg-3 {
  left: 650px;
  background: linear-gradient(90deg, rgba(79, 70, 229, 0.3), rgba(129, 140, 248, 0.05));
}

/* 头部Banner（机甲风格强化） */
.header-banner {
  background: linear-gradient(135deg, #04122f, #0a2a5a, #165DFF);
  border-radius: 16px;
  padding: 40px 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  overflow: hidden;
  margin-bottom: 36px;
  border: 2px solid #165DFF; /* 机甲蓝粗边框 */
  box-shadow: 0 15px 35px rgba(22, 93, 255, 0.2);
}
.header-banner::before {
  content: '';
  position: absolute;
  inset: 0;
  background: url('https://picsum.photos/id/1015/1600/800') center/cover;
  opacity: 0.12;
  filter: blur(2px);
}

.glow-pulse {
  position: absolute;
  top: 20%;
  left: 30%;
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(22,93,255,0.5), transparent 70%);
  border-radius: 50%;
  animation: pulseGlow 4s infinite;
  pointer-events: none;
}
@keyframes pulseGlow {
  0% { opacity: 0.2; transform: scale(0.8); }
  50% { opacity: 0.7; transform: scale(1.2); }
  100% { opacity: 0.2; transform: scale(0.8); }
}
.tech-line {
  position: absolute;
  background: linear-gradient(90deg, transparent, #4080FF, transparent);
}
.tech-line.top { top: 0; left: 10%; width: 80%; height: 2px; }
.tech-line.left { left: 0; top: 10%; width: 2px; height: 80%; }
.tech-line.right { right: 0; top: 10%; width: 2px; height: 80%; }
.banner-text { position: relative; z-index: 2; max-width: 55%; }
.banner-title { font-size: 44px; margin-bottom: 20px; font-weight: 700; text-shadow: 0 0 15px #4080FF; }
.banner-desc { font-size: 18px; opacity: 0.9; letter-spacing: 1px; }
.tech-mountain-img {
  width: 380px;
  filter: drop-shadow(0 10px 30px rgba(22,93,255,0.5));
  transition: 0.4s;
  border: 1px solid rgba(22,93,255,0.3);
  border-radius: 8px;
}
.header-banner:hover .tech-mountain-img { transform: translateY(-8px) scale(1.02); }

/* 布局 */
.row { display: flex; gap: 28px; width: 100%; margin-bottom: 32px; }
.card { flex: 1; }

/* 响应式 */
@media (max-width: 900px) {
  .row { flex-direction: column; }
  .header-banner { flex-direction: column; text-align: center; padding: 30px; }
  .banner-text, .banner-img { max-width: 100%; }
  .tech-mountain-img { width: 260px; }
  .flow-echarts-light, .flow-chart-wrapper { height: 600px; }
  .mecha-corner { width: 20px; height: 20px; }
  /* 移动端调整渐变背景层位置 */
  .layer-bg { width: 200px; }
  .layer-bg-1 { left: 20px; }
  .layer-bg-2 { left: 180px; }
  .layer-bg-3 { left: 340px; }
}
</style>
