import request from '@/utils/request'

// 查询MEI-人才招聘信息列表
export function listRecruit(query) {
  return request({
    url: '/mei/recruit/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-人才招聘信息详细
export function getRecruit(id) {
  return request({
    url: '/mei/recruit/' + id,
    method: 'get'
  })
}

// 新增MEI-人才招聘信息
export function addRecruit(data) {
  return request({
    url: '/mei/recruit',
    method: 'post',
    data: data
  })
}

// 修改MEI-人才招聘信息
export function updateRecruit(data) {
  return request({
    url: '/mei/recruit',
    method: 'put',
    data: data
  })
}

// 删除MEI-人才招聘信息
export function delRecruit(id) {
  return request({
    url: '/mei/recruit/' + id,
    method: 'delete'
  })
}
