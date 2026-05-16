const iconMap = {
  success: `<svg viewBox="0 0 64 64" width="56" height="56" fill="none"><circle cx="32" cy="32" r="30" fill="#E1F3D8" stroke="#67C23A" stroke-width="2"/><path d="M20 32l8 8 16-16" stroke="#67C23A" stroke-width="3.5" fill="none" stroke-linecap="round" stroke-linejoin="round"/></svg>`,
  error: `<svg viewBox="0 0 64 64" width="56" height="56" fill="none"><circle cx="32" cy="32" r="30" fill="#FDE2E2" stroke="#F56C6C" stroke-width="2"/><path d="M22 22l20 20M42 22L22 42" stroke="#F56C6C" stroke-width="3.5" stroke-linecap="round"/></svg>`,
  warning: `<svg viewBox="0 0 64 64" width="56" height="56" fill="none"><circle cx="32" cy="32" r="30" fill="#FAECD8" stroke="#E6A23C" stroke-width="2"/><path d="M32 20v18M32 44h0" stroke="#E6A23C" stroke-width="3.5" stroke-linecap="round"/></svg>`,
  info: `<svg viewBox="0 0 64 64" width="56" height="56" fill="none"><circle cx="32" cy="32" r="30" fill="#E6EFFC" stroke="#409EFF" stroke-width="2"/><path d="M32 28v14M32 20h0" stroke="#409EFF" stroke-width="3.5" stroke-linecap="round"/></svg>`
}

const titleMap = { success: '操作成功', error: '操作失败', warning: '请注意', info: '提示' }

export function notify(type, message, options = {}) {
  const { autoClose = true, duration = 2000, onOk } = options

  // 移除已有弹窗
  const existing = document.querySelector('.mei-notify-overlay')
  if (existing) existing.remove()

  // 创建遮罩层
  const overlay = document.createElement('div')
  overlay.className = 'mei-notify-overlay'
  overlay.style.cssText = `
    position: fixed; top: 0; left: 0; width: 100%; height: 100%;
    background: rgba(0,0,0,0.45); z-index: 9999;
    display: flex; align-items: center; justify-content: center;
    animation: meiFadeIn 0.25s ease;
  `

  // 创建弹窗
  const dialog = document.createElement('div')
  dialog.className = 'mei-notify-dialog'
  dialog.style.cssText = `
    background: #fff; border-radius: 20px; padding: 36px 40px 28px;
    min-width: 340px; max-width: 420px; text-align: center;
    box-shadow: 0 16px 56px rgba(0,0,0,0.22);
    position: relative;
    animation: meiScaleIn 0.3s cubic-bezier(0.34,1.56,0.64,1);
  `

  dialog.innerHTML = `
    <div style="margin-bottom:18px;">${iconMap[type] || iconMap.info}</div>
    <h3 style="font-size:18px;color:#303133;margin:0 0 12px 0;font-weight:700;">${titleMap[type] || '提示'}</h3>
    <p style="font-size:15px;color:#606266;margin:0;line-height:1.8;">${message}</p>
    ${!autoClose ? '<button class="mei-notify-btn" style="margin-top:22px;padding:10px 36px;border:none;border-radius:10px;background:linear-gradient(135deg,#C41E3A,#E85D75);color:#fff;font-size:15px;font-weight:500;cursor:pointer;box-shadow:0 4px 14px rgba(196,30,58,0.3);transition:all 0.2s;">知道了</button>' : ''}
  `

  overlay.appendChild(dialog)
  document.body.appendChild(overlay)

  // 关闭函数
  const close = () => {
    overlay.style.opacity = '0'
    overlay.style.transition = 'opacity 0.2s'
    setTimeout(() => overlay.remove(), 200)
    if (onOk) onOk()
  }

  // 点击遮罩关闭
  overlay.addEventListener('click', (e) => {
    if (e.target === overlay) close()
  })

  // "知道了"按钮
  const btn = dialog.querySelector('.mei-notify-btn')
  if (btn) {
    btn.addEventListener('click', close)
    btn.addEventListener('mouseenter', () => { btn.style.transform = 'translateY(-2px)'; btn.style.boxShadow = '0 6px 18px rgba(196,30,58,0.4)' })
    btn.addEventListener('mouseleave', () => { btn.style.transform = 'none'; btn.style.boxShadow = '0 4px 14px rgba(196,30,58,0.3)' })
  }

  // 自动关闭
  if (autoClose) {
    setTimeout(close, duration)
  }
}

export function showConfirm(message, options = {}) {
  const { title = '确认操作', confirmText = '确定', cancelText = '取消', type = 'warning' } = options

  return new Promise((resolve, reject) => {
    // 移除已有弹窗
    const existing = document.querySelector('.mei-notify-overlay')
    if (existing) existing.remove()

    const overlay = document.createElement('div')
    overlay.className = 'mei-notify-overlay'
    overlay.style.cssText = `
      position: fixed; top: 0; left: 0; width: 100%; height: 100%;
      background: rgba(0,0,0,0.45); z-index: 9999;
      display: flex; align-items: center; justify-content: center;
      animation: meiFadeIn 0.25s ease;
    `

    const dialog = document.createElement('div')
    dialog.className = 'mei-notify-dialog'
    dialog.style.cssText = `
      background: #fff; border-radius: 20px; padding: 36px 40px 28px;
      min-width: 340px; max-width: 420px; text-align: center;
      box-shadow: 0 16px 56px rgba(0,0,0,0.22);
      position: relative;
      animation: meiScaleIn 0.3s cubic-bezier(0.34,1.56,0.64,1);
    `

    dialog.innerHTML = `
      <div style="margin-bottom:18px;">${iconMap[type] || iconMap.warning}</div>
      <h3 style="font-size:18px;color:#303133;margin:0 0 12px 0;font-weight:700;">${title}</h3>
      <p style="font-size:15px;color:#606266;margin:0;line-height:1.8;">${message}</p>
      <div style="margin-top:24px;display:flex;gap:14px;justify-content:center;">
        <button class="mei-confirm-cancel" style="padding:10px 32px;border:1px solid #dcdfe6;border-radius:10px;background:#f5f7fa;color:#606266;font-size:14px;cursor:pointer;transition:all 0.2s;">${cancelText}</button>
        <button class="mei-confirm-ok" style="padding:10px 32px;border:none;border-radius:10px;background:linear-gradient(135deg,#C41E3A,#E85D75);color:#fff;font-size:14px;font-weight:500;cursor:pointer;box-shadow:0 4px 14px rgba(196,30,58,0.3);transition:all 0.2s;">${confirmText}</button>
      </div>
    `

    overlay.appendChild(dialog)
    document.body.appendChild(overlay)

    const cleanup = () => {
      overlay.style.opacity = '0'
      overlay.style.transition = 'opacity 0.2s'
      setTimeout(() => overlay.remove(), 200)
    }

    overlay.addEventListener('click', (e) => {
      if (e.target === overlay) { cleanup(); reject(false) }
    })

    dialog.querySelector('.mei-confirm-ok').addEventListener('click', () => { cleanup(); resolve(true) })
    dialog.querySelector('.mei-confirm-cancel').addEventListener('click', () => { cleanup(); reject(false) })
  })
}
