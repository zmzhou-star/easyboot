import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Vue Easy Boot'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
