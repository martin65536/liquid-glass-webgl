package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xr0 extends as0 implements Iterator {
    public yr0 e;
    public yr0 f;
    public final /* synthetic */ int g;

    public xr0(yr0 yr0Var, yr0 yr0Var2, int i) {
        this.g = i;
        this.e = yr0Var2;
        this.f = yr0Var;
    }

    @Override // defpackage.as0
    public final void a(yr0 yr0Var) {
        yr0 yr0Var2;
        yr0 yr0Var3 = null;
        if (this.e == yr0Var && yr0Var == this.f) {
            this.f = null;
            this.e = null;
        }
        yr0 yr0Var4 = this.e;
        if (yr0Var4 == yr0Var) {
            switch (this.g) {
                case 0:
                    yr0Var2 = yr0Var4.h;
                    break;
                default:
                    yr0Var2 = yr0Var4.g;
                    break;
            }
            this.e = yr0Var2;
        }
        yr0 yr0Var5 = this.f;
        if (yr0Var5 == yr0Var) {
            yr0 yr0Var6 = this.e;
            if (yr0Var5 != yr0Var6 && yr0Var6 != null) {
                yr0Var3 = b(yr0Var5);
            }
            this.f = yr0Var3;
        }
    }

    public final yr0 b(yr0 yr0Var) {
        switch (this.g) {
            case 0:
                return yr0Var.g;
            default:
                return yr0Var.h;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f != null) {
            return true;
        }
        return false;
    }

    @Override // java.util.Iterator
    public final Object next() {
        yr0 yr0Var;
        yr0 yr0Var2 = this.f;
        yr0 yr0Var3 = this.e;
        if (yr0Var2 != yr0Var3 && yr0Var3 != null) {
            yr0Var = b(yr0Var2);
        } else {
            yr0Var = null;
        }
        this.f = yr0Var;
        return yr0Var2;
    }
}
