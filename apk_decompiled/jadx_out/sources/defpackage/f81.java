package defpackage;

import com.kyant.backdrop.catalog.R;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f81 implements sh, h80 {
    public final b4 e;
    public final yh f;
    public boolean g;
    public l80 h;
    public kv i = ug.a;

    public f81(b4 b4Var, yh yhVar) {
        this.e = b4Var;
        this.f = yhVar;
    }

    public final void g() {
        if (!this.g) {
            this.g = true;
            this.e.getView().setTag(R.id.wrapped_composition_tag, null);
            l80 l80Var = this.h;
            if (l80Var != null) {
                l80Var.f(this);
            }
            this.h = null;
        }
        this.f.m();
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        if (z70Var == z70.ON_DESTROY) {
            g();
        } else if (z70Var == z70.ON_CREATE && !this.g) {
            i(this.i);
        }
    }

    public final void i(kv kvVar) {
        this.e.setOnReadyForComposition(new o6(8, this, kvVar));
    }
}
