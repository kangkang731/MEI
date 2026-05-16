import request from '@/utils/request'

// 查询MEI-融资申请服务列表
export function listFinance(query) {
  return request({
    url: '/mei/finance/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-融资申请服务详细
export function getFinance(id) {
  return request({
    url: '/mei/finance/' + id,
    method: 'get'
  })
}

// 新增MEI-融资申请服务
export function addFinance(data) {
  return request({
    url: '/mei/finance',
    method: 'post',
    data: data
  })
}

// 修改MEI-融资申请服务
export function updateFinance(data) {
  return request({
    url: '/mei/finance',
    method: 'put',
    data: data
  })
}

// 删除MEI-融资申请服务
export function delFinance(id) {
  return request({
    url: '/mei/finance/' + id,
    method: 'delete'
  })
}
