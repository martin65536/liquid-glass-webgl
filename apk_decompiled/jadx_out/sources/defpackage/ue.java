package defpackage;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.RenderEffect;
import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ue {
    public static final ve a = b(0.0f, 1.0f);

    public static final void a(np npVar, float f, float f2) {
        npVar.getClass();
        c(npVar, b(f, f2));
    }

    /* JADX WARN: Type inference failed for: r7v13, types: [te, ve] */
    public static final ve b(float f, float f2) {
        float f3 = ((0.5f - (f2 * 0.5f)) + f) * 255.0f;
        float f4 = (-0.1065f) * f2;
        float f5 = (-0.3575f) * f2;
        float f6 = (-0.036f) * f2;
        float f7 = f2 * 1.5f;
        float[] fArr = {f4 + f7, f5, f6, 0.0f, f3, f4, f5 + f7, f6, 0.0f, f3, f4, f5, f7 + f6, 0.0f, f3, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        ?? teVar = new te(new ColorMatrixColorFilter(fArr));
        teVar.b = fArr;
        return teVar;
    }

    public static final void c(np npVar, te teVar) {
        RenderEffect createColorFilterEffect;
        e6 e6Var;
        RenderEffect createColorFilterEffect2;
        npVar.getClass();
        teVar.getClass();
        if (Build.VERSION.SDK_INT >= 31) {
            gh ghVar = npVar.j;
            ColorFilter colorFilter = teVar.a;
            if (ghVar != null) {
                createColorFilterEffect2 = RenderEffect.createColorFilterEffect(colorFilter, ghVar.c());
                createColorFilterEffect2.getClass();
                e6Var = new e6(createColorFilterEffect2);
            } else {
                createColorFilterEffect = RenderEffect.createColorFilterEffect(colorFilter);
                createColorFilterEffect.getClass();
                e6Var = new e6(createColorFilterEffect);
            }
            npVar.j = e6Var;
        }
    }
}
