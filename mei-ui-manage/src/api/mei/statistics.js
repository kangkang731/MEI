import request from '@/utils/request'

// 查询MEI-双创数据统计总列表
export function listStatistics(query) {
  return request({
    url: '/mei/statistics/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-双创数据统计总详细
export function getStatistics(id) {
  return request({
    url: '/mei/statistics/' + id,
    method: 'get'
  })
}

// 新增MEI-双创数据统计总
export function addStatistics(data) {
  return request({
    url: '/mei/statistics',
    method: 'post',
    data: data
  })
}

// 修改MEI-双创数据统计总
export function updateStatistics(data) {
  return request({
    url: '/mei/statistics',
    method: 'put',
    data: data
  })
}

// 删除MEI-双创数据统计总
export function delStatistics(id) {
  return request({
    url: '/mei/statistics/' + id,
    method: 'delete'
  })
}
