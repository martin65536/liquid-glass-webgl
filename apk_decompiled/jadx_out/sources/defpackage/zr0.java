package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zr0 extends as0 implements Iterator {
    public yr0 e;
    public boolean f = true;
    public final /* synthetic */ xr g;

    public zr0(xr xrVar) {
        this.g = xrVar;
    }

    @Override // defpackage.as0
    public final void a(yr0 yr0Var) {
        boolean z;
        yr0 yr0Var2 = this.e;
        if (yr0Var == yr0Var2) {
            yr0 yr0Var3 = yr0Var2.h;
            this.e = yr0Var3;
            if (yr0Var3 == null) {
                z = true;
            } else {
                z = false;
            }
            this.f = z;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f) {
            if (this.g.e == null) {
                return false;
            }
            return true;
        }
        yr0 yr0Var = this.e;
        if (yr0Var == null || yr0Var.g == null) {
            return false;
        }
        return true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        yr0 yr0Var;
        if (this.f) {
            this.f = false;
            this.e = this.g.e;
        } else {
            yr0 yr0Var2 = this.e;
            if (yr0Var2 != null) {
                yr0Var = yr0Var2.g;
            } else {
                yr0Var = null;
            }
            this.e = yr0Var;
        }
        return this.e;
    }
}
