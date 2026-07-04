package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class co0 extends jc implements t30 {
    public final boolean k;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public co0(java.lang.Object r9, java.lang.Class r10, java.lang.String r11, java.lang.String r12, int r13) {
        /*
            r8 = this;
            r0 = 1
            r13 = r13 & r0
            r1 = 0
            if (r13 != r0) goto Lc
            r7 = r0
        L6:
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            goto Le
        Lc:
            r7 = r1
            goto L6
        Le:
            r2.<init>(r3, r4, r5, r6, r7)
            r2.k = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.co0.<init>(java.lang.Object, java.lang.Class, java.lang.String, java.lang.String, int):void");
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof co0) {
                co0 co0Var = (co0) obj;
                if (g().equals(co0Var.g()) && this.h.equals(co0Var.h) && this.i.equals(co0Var.i) && o20.e(this.f, co0Var.f)) {
                    return true;
                }
                return false;
            }
            if (obj instanceof t30) {
                return obj.equals(i());
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.i.hashCode() + ((this.h.hashCode() + (g().hashCode() * 31)) * 31);
    }

    public final p30 i() {
        if (this.k) {
            return this;
        }
        p30 p30Var = this.e;
        if (p30Var == null) {
            p30 f = f();
            this.e = f;
            return f;
        }
        return p30Var;
    }

    public final String toString() {
        p30 i = i();
        if (i != this) {
            return i.toString();
        }
        return "property " + this.h + " (Kotlin reflection is not available)";
    }
}
