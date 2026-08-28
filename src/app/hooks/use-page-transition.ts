import * as React from 'react'
import { CatalogDestination } from '@/components/liquid-glass/catalog'

/* ------------------------------------------------------------------ *
 * Page transition animation: 3-phase directional slide.
 *
 * Phases:
 *   fadeOut   — old content slides out + fades to 0
 *   prepIn    — instantly place new content at opposite offset + opacity 0 (no transition)
 *   fadeIn    — animate new content from offset → center + opacity 0 → 1
 *
 * Enter: old page slides LEFT out, new page slides in from RIGHT.
 * Exit:  old page slides RIGHT out, new page slides in from LEFT.
 * ------------------------------------------------------------------ */

const TRANSITION_MS = 200 // duration for each phase (fade out / fade in)
const OFFSET_PX = 16 // slide distance in px

interface UsePageTransitionOpts {
  pageTransition: boolean
  setDestination: React.Dispatch<React.SetStateAction<CatalogDestination>>
}

export function usePageTransition({ pageTransition, setDestination }: UsePageTransitionOpts) {
  const [transPhase, setTransPhase] = React.useState<'idle' | 'fadeOut' | 'prepIn' | 'fadeIn'>('idle')
  const transDirRef = React.useRef<'enter' | 'exit'>('enter')
  const pendingDestRef = React.useRef<CatalogDestination | null>(null)

  const onNavigate = React.useCallback((d: CatalogDestination) => {
    if (!pageTransition) {
      setDestination(d)
    } else {
      // Enter: old page slides LEFT out, new page slides in from RIGHT.
      pendingDestRef.current = d
      transDirRef.current = 'enter'
      setTransPhase('fadeOut')
    }
    if (typeof window !== 'undefined' && d !== CatalogDestination.Home) {
      window.history.pushState({ dest: d }, '')
    }
  }, [pageTransition])

  const onBack = React.useCallback(() => {
    const target = CatalogDestination.Home
    if (!pageTransition) {
      setDestination(target)
    } else {
      // Exit: old page slides RIGHT out, new page slides in from LEFT.
      pendingDestRef.current = target
      transDirRef.current = 'exit'
      setTransPhase('fadeOut')
    }
    if (typeof window !== 'undefined' && window.history.state?.dest !== undefined) {
      window.history.back()
    }
  }, [pageTransition])

  // Phase progression: fadeOut → prepIn → fadeIn → idle
  React.useEffect(() => {
    if (transPhase === 'fadeOut') {
      const timer = setTimeout(() => {
        const dest = pendingDestRef.current ?? CatalogDestination.Home
        setDestination(dest)
        pendingDestRef.current = null
        // prepIn: place new content at opposite offset with no transition
        setTransPhase('prepIn')
      }, TRANSITION_MS)
      return () => clearTimeout(timer)
    }
    if (transPhase === 'prepIn') {
      // After React renders the offset position (1 frame), start animated fadeIn
      requestAnimationFrame(() => {
        requestAnimationFrame(() => {
          setTransPhase('fadeIn')
        })
      })
      return
    }
    if (transPhase === 'fadeIn') {
      const timer = setTimeout(() => {
        setTransPhase('idle')
      }, TRANSITION_MS)
      return () => clearTimeout(timer)
    }
  }, [transPhase])

  // Listen for browser back gesture / button → return to Home with exit animation.
  React.useEffect(() => {
    if (typeof window === 'undefined') return
    const onPopState = () => {
      // If a transition is already in progress, skip — onBack already started it
      // and the history.back() call triggered this popstate as a side effect.
      if (transPhase !== 'idle') return
      if (!pageTransition) {
        setDestination(CatalogDestination.Home)
      } else {
        // Trigger the same exit animation as onBack
        pendingDestRef.current = CatalogDestination.Home
        transDirRef.current = 'exit'
        setTransPhase('fadeOut')
      }
    }
    window.addEventListener('popstate', onPopState)
    return () => window.removeEventListener('popstate', onPopState)
  }, [pageTransition, transPhase])

  // Compute the frame's opacity/transform/transition for the current phase.
  // Spread this object into the frame element's inline style.
  //
  // transDirRef.current is read here (during render) — this is safe because
  // transDirRef is set synchronously alongside setTransPhase in the same
  // event handler, so by the time this render runs the ref already reflects
  // the new direction matching the new transPhase. There is no race.
  /* eslint-disable react-hooks/refs */
  const transStyle: React.CSSProperties = {
    opacity: (() => {
      if (transPhase === 'fadeOut' || transPhase === 'prepIn') return 0
      return 1 // idle or fadeIn
    })(),
    transform: (() => {
      const dir = transDirRef.current
      if (transPhase === 'fadeOut') {
        // Old content exits: enter→slides LEFT, exit→slides RIGHT
        return dir === 'enter'
          ? `translateX(-${OFFSET_PX}px)`
          : `translateX(${OFFSET_PX}px)`
      }
      if (transPhase === 'prepIn') {
        // New content placed at opposite side instantly (no transition)
        // Enter→placed RIGHT offset, Exit→placed LEFT offset
        return dir === 'enter'
          ? `translateX(${OFFSET_PX}px)`
          : `translateX(-${OFFSET_PX}px)`
      }
      // idle / fadeIn → centered
      return 'translateX(0)'
    })(),
    transition: (() => {
      if (!pageTransition) return 'none'
      if (transPhase === 'prepIn') return 'none' // instant placement, no animation
      return `opacity ${TRANSITION_MS}ms ease, transform ${TRANSITION_MS}ms ease`
    })(),
  }
  /* eslint-enable react-hooks/refs */

  return { onNavigate, onBack, transPhase, transStyle }
}
