package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class be extends gd0 {
    public final je0 a;
    public final a00 b;
    public final boolean c;
    public final cr0 d;
    public final vu e;

    public be(je0 je0Var, a00 a00Var, boolean z, cr0 cr0Var, vu vuVar) {
        this.a = je0Var;
        this.b = a00Var;
        this.c = z;
        this.d = cr0Var;
        this.e = vuVar;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new de(this.a, this.b, this.c, this.d, this.e);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && be.class == obj.getClass()) {
                be beVar = (be) obj;
                if (!o20.e(this.a, beVar.a) || !o20.e(this.b, beVar.b) || this.c != beVar.c || !o20.e(this.d, beVar.d) || this.e != beVar.e) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0068, code lost:
    
        if (r7.D == null) goto L34;
     */
    @Override // defpackage.gd0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void f(defpackage.bd0 r7) {
        /*
            r6 = this;
            de r7 = (defpackage.de) r7
            tt r0 = r7.A
            je0 r1 = r7.J
            je0 r2 = r6.a
            boolean r1 = defpackage.o20.e(r1, r2)
            r3 = 1
            r4 = 0
            if (r1 != 0) goto L19
            r7.H0()
            r7.J = r2
            r7.u = r2
            r1 = r3
            goto L1a
        L19:
            r1 = r4
        L1a:
            a00 r2 = r7.v
            a00 r5 = r6.b
            boolean r2 = defpackage.o20.e(r2, r5)
            if (r2 != 0) goto L27
            r7.v = r5
            r1 = r3
        L27:
            boolean r2 = r7.w
            boolean r5 = r6.c
            if (r2 == r5) goto L35
            r7.w = r5
            if (r5 == 0) goto L34
            r7.P()
        L34:
            r1 = r3
        L35:
            boolean r2 = r7.y
            if (r2 == r3) goto L41
            r7.D0(r0)
            defpackage.m20.w(r7)
            r7.y = r3
        L41:
            cr0 r2 = r7.x
            cr0 r5 = r6.d
            boolean r2 = defpackage.o20.e(r2, r5)
            if (r2 != 0) goto L50
            r7.x = r5
            defpackage.m20.w(r7)
        L50:
            vu r6 = r6.e
            r7.z = r6
            boolean r6 = r7.K
            je0 r2 = r7.J
            if (r2 != 0) goto L5c
            r5 = r3
            goto L5d
        L5c:
            r5 = r4
        L5d:
            if (r6 == r5) goto L6b
            if (r2 != 0) goto L62
            r4 = r3
        L62:
            r7.K = r4
            if (r4 != 0) goto L6b
            im r6 = r7.D
            if (r6 != 0) goto L6b
            goto L6c
        L6b:
            r3 = r1
        L6c:
            if (r3 == 0) goto L81
            im r6 = r7.D
            if (r6 != 0) goto L76
            boolean r1 = r7.K
            if (r1 != 0) goto L81
        L76:
            if (r6 == 0) goto L7b
            r7.E0(r6)
        L7b:
            r6 = 0
            r7.D = r6
            r7.J0()
        L81:
            je0 r6 = r7.u
            r0.H0(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.be.f(bd0):void");
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        je0 je0Var = this.a;
        if (je0Var != null) {
            i = je0Var.hashCode();
        } else {
            i = 0;
        }
        int i5 = i * 31;
        a00 a00Var = this.b;
        if (a00Var != null) {
            i2 = a00Var.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 31;
        if (this.c) {
            i3 = 1231;
        } else {
            i3 = 1237;
        }
        int i7 = (((i6 + i3) * 31) + 1231) * 961;
        cr0 cr0Var = this.d;
        if (cr0Var != null) {
            i4 = cr0Var.a;
        }
        return this.e.hashCode() + ((i7 + i4) * 31);
    }
}
