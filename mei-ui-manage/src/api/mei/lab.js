import request from '@/utils/request'

// 查询MEI-西藏高校实验室列表
export function listLab(query) {
  return request({
    url: '/mei/lab/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-西藏高校实验室详细
export function getLab(id) {
  return request({
    url: '/mei/lab/' + id,
    method: 'get'
  })
}

// 新增MEI-西藏高校实验室
export function addLab(data) {
  return request({
    url: '/mei/lab',
    method: 'post',
    data: data
  })
}

// 修改MEI-西藏高校实验室
export function updateLab(data) {
  return request({
    url: '/mei/lab',
    method: 'put',
    data: data
  })
}

// 删除MEI-西藏高校实验室
export function delLab(id) {
  return request({
    url: '/mei/lab/' + id,
    method: 'delete'
  })
}
