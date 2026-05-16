import request from '@/utils/request'

// 查询MEI-平台互动交流列表
export function listMessage(query) {
  return request({
    url: '/mei/message/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-平台互动交流详细
export function getMessage(id) {
  return request({
    url: '/mei/message/' + id,
    method: 'get'
  })
}

// 新增MEI-平台互动交流
export function addMessage(data) {
  return request({
    url: '/mei/message',
    method: 'post',
    data: data
  })
}

// 修改MEI-平台互动交流
export function updateMessage(data) {
  return request({
    url: '/mei/message',
    method: 'put',
    data: data
  })
}

// 删除MEI-平台互动交流
export function delMessage(id) {
  return request({
    url: '/mei/message/' + id,
    method: 'delete'
  })
}
