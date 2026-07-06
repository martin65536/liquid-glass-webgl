package defpackage;

import android.graphics.RenderEffect;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ia extends gh {
    public final gh b;
    public final float c;
    public final float d;
    public final int e;

    public ia(gh ghVar, float f, float f2, int i) {
        this.b = ghVar;
        this.c = f;
        this.d = f2;
        this.e = i;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ia) {
                ia iaVar = (ia) obj;
                if (this.c == iaVar.c && this.d == iaVar.d && this.e == iaVar.e && o20.e(this.b, iaVar.b)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gh
    public final RenderEffect f() {
        RenderEffect createBlurEffect;
        RenderEffect createBlurEffect2;
        RenderEffect createOffsetEffect;
        float f = this.c;
        float f2 = this.d;
        if (f == 0.0f && f2 == 0.0f) {
            createOffsetEffect = RenderEffect.createOffsetEffect(0.0f, 0.0f);
            return createOffsetEffect;
        }
        gh ghVar = this.b;
        int i = this.e;
        if (ghVar == null) {
            createBlurEffect2 = RenderEffect.createBlurEffect(f, f2, jc0.I(i));
            return createBlurEffect2;
        }
        createBlurEffect = RenderEffect.createBlurEffect(f, f2, ghVar.c(), jc0.I(i));
        return createBlurEffect;
    }

    public final int hashCode() {
        int i;
        gh ghVar = this.b;
        if (ghVar != null) {
            i = ghVar.hashCode();
        } else {
            i = 0;
        }
        return d3.s(this.d, d3.s(this.c, i * 31, 31), 31) + this.e;
    }

    public final String toString() {
        return "BlurEffect(renderEffect=" + this.b + ", radiusX=" + this.c + ", radiusY=" + this.d + ", edgeTreatment=" + ((Object) t20.S(this.e)) + ')';
    }
}
