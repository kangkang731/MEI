import request from '@/utils/request'

// 查询成果列表
export function listAchievement(query) {
  return request({
    url: '/mei/achievement/list',
    method: 'get',
    params: query
  })
}

// 查询成果详细
export function getAchievement(id) {
  return request({
    url: '/mei/achievement/' + id,
    method: 'get'
  })
}

// 新增成果
export function addAchievement(data) {
  return request({
    url: '/mei/achievement',
    method: 'post',
    data: data
  })
}

// 修改成果
export function updateAchievement(data) {
  return request({
    url: '/mei/achievement',
    method: 'put',
    data: data
  })
}

// 删除成果
export function delAchievement(id) {
  return request({
    url: '/mei/achievement/' + id,
    method: 'delete'
  })
}
