package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cm implements lt0 {
    public final /* synthetic */ dm a;

    public cm(dm dmVar) {
        this.a = dmVar;
    }

    @Override // defpackage.lt0
    public final float a(float f) {
        boolean z;
        if (Float.isNaN(f)) {
            return 0.0f;
        }
        dm dmVar = this.a;
        float floatValue = ((Number) dmVar.a.e(Float.valueOf(f))).floatValue();
        ik0 ik0Var = dmVar.e;
        boolean z2 = false;
        if (floatValue > 0.0f) {
            z = true;
        } else {
            z = false;
        }
        ik0Var.setValue(Boolean.valueOf(z));
        ik0 ik0Var2 = dmVar.f;
        if (floatValue < 0.0f) {
            z2 = true;
        }
        ik0Var2.setValue(Boolean.valueOf(z2));
        return floatValue;
    }
}
