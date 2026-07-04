package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class k2 implements tv, Serializable {
    public final Object e;
    public final Class f;
    public final String g;
    public final String h;
    public final boolean i = false;
    public final int j;
    public final int k;

    public k2(int i, int i2, Class cls, Object obj, String str, String str2) {
        this.e = obj;
        this.f = cls;
        this.g = str;
        this.h = str2;
        this.j = i;
        this.k = i2 >> 1;
    }

    @Override // defpackage.tv
    public final int b() {
        return this.j;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof k2) {
                k2 k2Var = (k2) obj;
                if (this.i == k2Var.i && this.j == k2Var.j && this.k == k2Var.k && this.e.equals(k2Var.e) && this.f.equals(k2Var.f) && this.g.equals(k2Var.g) && this.h.equals(k2Var.h)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int hashCode = (this.h.hashCode() + ((this.g.hashCode() + ((this.f.hashCode() + (this.e.hashCode() * 31)) * 31)) * 31)) * 31;
        if (this.i) {
            i = 1231;
        } else {
            i = 1237;
        }
        return ((((hashCode + i) * 31) + this.j) * 31) + this.k;
    }

    public final String toString() {
        fp0.a.getClass();
        return gp0.a(this);
    }
}
