import type { LiquidGlassRenderer } from './index'

/* ------------------------------------------------------------------ *
 * cacheUniforms — look up every program's uniform locations once and
 * cache them in the this.u* record fields. Extracted from index.ts
 * (was ~114 LOC inline in the class body).
 * ------------------------------------------------------------------ */

declare module './index' {
  interface LiquidGlassRenderer {
    cacheUniforms(): void
  }
}

export const uniformMethods = {
  cacheUniforms(this: LiquidGlassRenderer) {
    const gl = this.gl
    const elNames = [
      'uBackdrop', 'uWallpaperSampler', 'uTabsBackdropSampler', 'uCanvasSize', 'uWallpaperSize', 'uElementOffset', 'uElementSize',
      'uCornerRadii', 'uRefractionHeight', 'uRefractionAmount', 'uDepthEffect',
      'uChromaticAberration', 'uBlurRadius', 'uSaturation', 'uBrightness',
      'uContrast', 'uTintColor', 'uSurfaceColor', 'uHighlightColor',
      'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uHighlightStrokeWidth', 'uHighlightBlur',
      'uContentScaleX', 'uContentScaleY',
      'uUseToggleBackdrop', 'uUseSolidBackdrop', 'uSolidBackdropColor',
      'uTrackColor', 'uTrackRect', 'uTrackCornerRadius',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale',
      'uIndicatorBackdrop', 'uContainerRect', 'uContainerCornerRadius', 'uIndicatorAccent',
      'uInsetPx', 'uIndicatorPressProgress', 'uIndicatorPanelOffset', 'uDpr',
      'uContainerCenter', 'uContainerScale',
      'uTabContentTex0', 'uTabContentTex1', 'uTabContentTex2', 'uTabContentTex3',
      'uTabContentTex4', 'uTabContentTex5', 'uTabContentTex6', 'uTabContentTex7',
      'uTabContentRects[0]', 'uTabContentRects[1]', 'uTabContentRects[2]', 'uTabContentRects[3]',
      'uTabContentRects[4]', 'uTabContentRects[5]', 'uTabContentRects[6]', 'uTabContentRects[7]',
      'uTabContentCount', 'uTabsGlassLayer',
      'uSdfTexSampler', 'uUseSdfTexture', 'uSdfTexSize', 'uSdfLightAngle', 'uEnterAlpha',
      'uSdfHighlightScale', 'uSdfBevelEnabled', 'uSdfGlassTintHue', 'uSdfEdgeMatteEnabled', 'uSdfDebugMode', 'uSdfAaMin',
      'uUsePerElementFbo', 'uSceneRectOffset', 'uElFboSize', 'uBackdropRect',
      'uCornerStyle', 'uSkipColorControls',
      'uUseMagnifier', 'uMagnifierZoom', 'uMagnifierOffsetY',
      'uElementRotation',
      'uContinuousSdf', 'uUseContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
      'uNoContinuousSdfInRefraction',
      'uInnerStrokeMask', 'uInnerStrokeMaskOffset', 'uInnerStrokeMaskSize',
    ]
    for (const n of elNames) this.uEl[n] = gl.getUniformLocation(this.elementProgram, n)
    const shNames = [
      'uCanvasSize', 'uElementOffset', 'uElementSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowOffset', 'uShadowColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
    ]
    for (const n of shNames) this.uSh[n] = gl.getUniformLocation(this.shadowProgram, n)
    const wpNames = ['uBackdrop', 'uCanvasSize', 'uWallpaperSize']
    for (const n of wpNames) this.uWp[n] = gl.getUniformLocation(this.wallpaperProgram, n)
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uAlpha',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foregroundProgram, n)
    const hlNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uRadius', 'uPosition',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle']
    for (const n of hlNames) this.uHl[n] = gl.getUniformLocation(this.highlightProgram, n)
    const tnNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle']
    for (const n of tnNames) this.uTn[n] = gl.getUniformLocation(this.tintProgram, n)
    const rmNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff',
      'uHighlightAlpha', 'uHighlightMode', 'uHighlightStrokeWidth',
      'uHighlightBlur',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of rmNames) this.uRm[n] = gl.getUniformLocation(this.rimHighlightProgram, n)
    // Highlight stroke pass (pass 1): renders the clipped stroke alpha mask.
    const hsNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uHighlightStrokeWidth',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of hsNames) this.uHs[n] = gl.getUniformLocation(this.highlightStrokeProgram, n)
    // Highlight composite pass (pass 3): samples blurred mask, multiplies intensity+color.
    const hcNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uBlurredMask', 'uMaskTexSize',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of hcNames) this.uHc[n] = gl.getUniformLocation(this.highlightCompositeProgram, n)
    // Stroke mask composite (Canvas2D stroke mask approach)
    const smNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uStrokeMask', 'uMaskOffset', 'uMaskSize',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
    ]
    for (const n of smNames) this.uSm[n] = gl.getUniformLocation(this.strokeMaskCompositeProgram, n)
    // Inner shadow mask composite (Canvas2D ring mask approach)
    const isNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uInnerShadowMask', 'uMaskOffset', 'uMaskSize',
      'uInnerShadowColor', 'uInnerShadowAlpha',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
    ]
    for (const n of isNames) this.uIs[n] = gl.getUniformLocation(this.innerShadowMaskCompositeProgram, n)
    const prNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize']
    for (const n of prNames) this.uPr[n] = gl.getUniformLocation(this.plainRectProgram, n)
    const pbNames = [
      'uBackdrop', 'uCanvasSize', 'uWallpaperSize', 'uOffset', 'uSize',
      'uBlurRadius', 'uTintColor', 'uTintIntensity',
    ]
    for (const n of pbNames) this.uPb[n] = gl.getUniformLocation(this.progressiveBlurProgram, n)
    const cpNames = ['uTexture', 'uCanvasSize']
    for (const n of cpNames) this.uCp[n] = gl.getUniformLocation(this.copyProgram, n)
    const sfNames = ['uColor']
    for (const n of sfNames) this.uSf[n] = gl.getUniformLocation(this.solidFillProgram, n)
    const ccNames = ['uTexture', 'uTexSize', 'uBrightness', 'uContrast', 'uSaturation']
    for (const n of ccNames) this.uCc[n] = gl.getUniformLocation(this.colorControlsProgram, n)
    const stNames = ['uTexture', 'uCanvasSize', 'uTintColor']
    for (const n of stNames) this.uSt[n] = gl.getUniformLocation(this.sceneTintProgram, n)
    const efNames = ['uTexture', 'uCanvasSize', 'uElementCenter', 'uElementSize', 'uRotation', 'uSrcSize']
    for (const n of efNames) this.uEf[n] = gl.getUniformLocation(this.elFboCompositeProgram, n)
    const ecNames = ['uTexture', 'uSrcOffset', 'uSrcSize', 'uDstSize']
    for (const n of ecNames) this.uEc[n] = gl.getUniformLocation(this.elFboCropProgram, n)
  },
} as const
