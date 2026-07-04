package defpackage;

import android.graphics.RenderEffect;
import android.graphics.RuntimeShader;
import android.graphics.text.LineBreakConfig;
import android.provider.MediaStore;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class d1 {
    public static /* bridge */ /* synthetic */ int a() {
        return MediaStore.getPickImagesMaxLimit();
    }

    public static /* bridge */ /* synthetic */ RenderEffect b(RuntimeShader runtimeShader) {
        return RenderEffect.createRuntimeShaderEffect(runtimeShader, "content");
    }

    public static /* synthetic */ LineBreakConfig.Builder c() {
        return new LineBreakConfig.Builder();
    }

    public static /* bridge */ /* synthetic */ LineBreakConfig.Builder d(LineBreakConfig.Builder builder, int i) {
        return builder.setLineBreakStyle(i);
    }

    public static /* bridge */ /* synthetic */ LineBreakConfig e(LineBreakConfig.Builder builder) {
        return builder.build();
    }

    public static /* synthetic */ BoringLayout g(CharSequence charSequence, TextPaint textPaint, int i, Layout.Alignment alignment, BoringLayout.Metrics metrics, boolean z, TextUtils.TruncateAt truncateAt, int i2) {
        return new BoringLayout(charSequence, textPaint, i, alignment, 1.0f, 0.0f, metrics, z, truncateAt, i2, true);
    }

    public static /* bridge */ /* synthetic */ AccessibilityNodeInfo.AccessibilityAction h() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS;
    }

    public static /* bridge */ /* synthetic */ void k(RuntimeShader runtimeShader, String str, float f) {
        runtimeShader.setFloatUniform(str, f);
    }

    public static /* bridge */ /* synthetic */ void l(RuntimeShader runtimeShader, String str, float f, float f2) {
        runtimeShader.setFloatUniform(str, f, f2);
    }

    public static /* bridge */ /* synthetic */ void n(RuntimeShader runtimeShader, float[] fArr) {
        runtimeShader.setFloatUniform("cornerRadii", fArr);
    }

    public static /* bridge */ /* synthetic */ void o(StaticLayout.Builder builder, LineBreakConfig lineBreakConfig) {
        builder.setLineBreakConfig(lineBreakConfig);
    }

    public static /* bridge */ /* synthetic */ LineBreakConfig.Builder t(LineBreakConfig.Builder builder, int i) {
        return builder.setLineBreakWordStyle(i);
    }
}
