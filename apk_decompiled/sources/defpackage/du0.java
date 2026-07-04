package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class du0 implements lt0 {
    public final /* synthetic */ hu0 a;
    public final /* synthetic */ fu0 b;

    public du0(hu0 hu0Var, fu0 fu0Var) {
        this.a = hu0Var;
        this.b = fu0Var;
    }

    @Override // defpackage.lt0
    public final float a(float f) {
        float abs = Math.abs(f);
        hu0 hu0Var = this.a;
        if (abs == 0.0f || ((Boolean) hu0Var.h.a()).booleanValue()) {
            return hu0Var.d(hu0Var.g(this.b.a(2, hu0Var.e(hu0Var.h(f)))));
        }
        throw new lm0("The fling animation was cancelled", 0);
    }
}
