import request from '@/utils/request'

// 查询MEI-创新创业项目申报列表
export function listProject(query) {
  return request({
    url: '/mei/project/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-创新创业项目申报详细
export function getProject(id) {
  return request({
    url: '/mei/project/' + id,
    method: 'get'
  })
}

// 新增MEI-创新创业项目申报
export function addProject(data) {
  return request({
    url: '/mei/project',
    method: 'post',
    data: data
  })
}

// 修改MEI-创新创业项目申报
export function updateProject(data) {
  return request({
    url: '/mei/project',
    method: 'put',
    data: data
  })
}

// 删除MEI-创新创业项目申报
export function delProject(id) {
  return request({
    url: '/mei/project/' + id,
    method: 'delete'
  })
}
