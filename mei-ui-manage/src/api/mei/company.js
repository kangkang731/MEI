import request from '@/utils/request'

// 查询MEI-入驻企业信息列表
export function listCompany(query) {
  return request({
    url: '/mei/company/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-入驻企业信息详细
export function getCompany(id) {
  return request({
    url: '/mei/company/' + id,
    method: 'get'
  })
}

// 新增MEI-入驻企业信息
export function addCompany(data) {
  return request({
    url: '/mei/company',
    method: 'post',
    data: data
  })
}

// 修改MEI-入驻企业信息
export function updateCompany(data) {
  return request({
    url: '/mei/company',
    method: 'put',
    data: data
  })
}

// 删除MEI-入驻企业信息
export function delCompany(id) {
  return request({
    url: '/mei/company/' + id,
    method: 'delete'
  })
}
