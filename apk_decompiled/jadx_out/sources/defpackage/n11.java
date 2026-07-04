package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n11 extends gd0 {
    public final String a;
    public final r11 b;
    public final wt c;
    public final int d;
    public final u2 e;

    public n11(String str, r11 r11Var, wt wtVar, int i, u2 u2Var) {
        this.a = str;
        this.b = r11Var;
        this.c = wtVar;
        this.d = i;
        this.e = u2Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [q11, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.b;
        bd0Var.u = this.c;
        bd0Var.v = 1;
        bd0Var.w = true;
        bd0Var.x = this.d;
        bd0Var.y = 1;
        bd0Var.z = this.e;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof n11) {
            n11 n11Var = (n11) obj;
            if (o20.e(this.e, n11Var.e) && this.a.equals(n11Var.a) && o20.e(this.b, n11Var.b) && o20.e(this.c, n11Var.c) && this.d == n11Var.d) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0053, code lost:
    
        if (r7.a.a(r5.a) != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0022, code lost:
    
        if (r5.a.b(r2.a) != false) goto L10;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0093 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0038  */
    @Override // defpackage.gd0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void f(defpackage.bd0 r18) {
        /*
            Method dump skipped, instructions count: 204
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n11.f(bd0):void");
    }

    public final int hashCode() {
        int i;
        int hashCode = (((((((((this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31) + 1) * 31) + 1231) * 31) + this.d) * 31) + 1) * 31;
        u2 u2Var = this.e;
        if (u2Var != null) {
            i = u2Var.hashCode();
        } else {
            i = 0;
        }
        return hashCode + i;
    }
}
