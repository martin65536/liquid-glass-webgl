import type { CSSProperties } from 'react'

export const btnStyle: CSSProperties = {
  background: 'rgba(255,255,255,0.08)',
  border: '1px solid rgba(255,255,255,0.2)',
  color: '#e8e8e8',
  font: '11px ui-monospace, monospace',
  padding: '2px 6px',
  borderRadius: 4,
  cursor: 'pointer',
  lineHeight: 1,
}

// Scrollable body container: flex-1 so it fills the panel's remaining height
// (panel maxHeight = vpHeight - 16, where vpHeight tracks visualViewport so
// mobile browser chrome is excluded) and scrolls vertically when the content
// (chart + sections + toggles + buttons) overflows. Custom scrollbar styling
// keeps it unobtrusive on the dark panel.
export const scrollBodyStyle: CSSProperties = {
  flex: 1,
  minHeight: 0, // critical: lets flex child shrink below content height
  overflowY: 'auto',
  scrollbarWidth: 'thin',
  scrollbarColor: 'rgba(255,255,255,0.25) transparent',
}
