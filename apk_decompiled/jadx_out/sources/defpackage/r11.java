package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r11 {
    public static final r11 d = new r11(0, 0, null, 16777215);
    public final ux0 a;
    public final ck0 b;
    public final nm0 c;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public r11(long r22, long r24, defpackage.nu r26, int r27) {
        /*
            r21 = this;
            r0 = r27 & 1
            if (r0 == 0) goto L8
            long r0 = defpackage.se.g
            r3 = r0
            goto La
        L8:
            r3 = r22
        La:
            r0 = r27 & 2
            if (r0 == 0) goto L12
            long r0 = defpackage.t11.c
            r5 = r0
            goto L14
        L12:
            r5 = r24
        L14:
            r0 = r27 & 4
            r1 = 0
            if (r0 == 0) goto L1b
            r7 = r1
            goto L1d
        L1b:
            r7 = r26
        L1d:
            long r10 = defpackage.t11.c
            long r17 = defpackage.se.g
            r0 = 32768(0x8000, float:4.5918E-41)
            r0 = r27 & r0
            if (r0 == 0) goto L2a
            r0 = 0
            goto L2b
        L2a:
            r0 = 3
        L2b:
            ux0 r2 = new ux0
            r8 = 0
            r9 = 0
            r12 = r10
            r10 = 0
            r11 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r19 = 0
            r20 = 0
            r2.<init>(r3, r5, r7, r8, r9, r10, r11, r12, r14, r15, r16, r17, r19, r20)
            ck0 r7 = new ck0
            r9 = 0
            r10 = r12
            r12 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r8 = r0
            r13 = r1
            r7.<init>(r8, r9, r10, r12, r13, r14, r15, r16, r17)
            r0 = 0
            r1 = r21
            r1.<init>(r2, r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.r11.<init>(long, long, nu, int):void");
    }

    public static r11 a(r11 r11Var, long j) {
        long j2 = t11.c;
        ux0 a = vx0.a(r11Var.a, j, null, Float.NaN, j2, null, null, null, null, null, j2, null, null, null, se.g, null, null, null);
        ck0 a2 = dk0.a(r11Var.b, 0, 0, j2, null, null, null, 0, 0, null);
        if (r11Var.a == a && r11Var.b == a2) {
            return r11Var;
        }
        return new r11(a, a2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof r11)) {
            return false;
        }
        r11 r11Var = (r11) obj;
        if (o20.e(this.a, r11Var.a) && o20.e(this.b, r11Var.b) && o20.e(this.c, r11Var.c)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int hashCode = (this.b.hashCode() + (this.a.hashCode() * 31)) * 31;
        nm0 nm0Var = this.c;
        if (nm0Var != null) {
            i = nm0Var.hashCode();
        } else {
            i = 0;
        }
        return hashCode + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TextStyle(color=");
        ux0 ux0Var = this.a;
        sb.append((Object) se.j(ux0Var.a.a()));
        sb.append(", brush=");
        sb.append(ux0Var.a.d());
        sb.append(", alpha=");
        sb.append(ux0Var.a.r());
        sb.append(", fontSize=");
        sb.append((Object) t11.e(ux0Var.b));
        sb.append(", fontWeight=");
        sb.append(ux0Var.c);
        sb.append(", fontStyle=");
        sb.append(ux0Var.d);
        sb.append(", fontSynthesis=");
        sb.append(ux0Var.e);
        sb.append(", fontFamily=");
        sb.append(ux0Var.f);
        sb.append(", fontFeatureSettings=");
        sb.append(ux0Var.g);
        sb.append(", letterSpacing=");
        sb.append((Object) t11.e(ux0Var.h));
        sb.append(", baselineShift=");
        sb.append(ux0Var.i);
        sb.append(", textGeometricTransform=");
        sb.append(ux0Var.j);
        sb.append(", localeList=");
        sb.append(ux0Var.k);
        sb.append(", background=");
        sb.append((Object) se.j(ux0Var.l));
        sb.append(", textDecoration=");
        sb.append(ux0Var.m);
        sb.append(", shadow=");
        sb.append(ux0Var.n);
        sb.append(", drawStyle=");
        sb.append(ux0Var.o);
        sb.append(", textAlign=");
        ck0 ck0Var = this.b;
        sb.append((Object) t01.a(ck0Var.a));
        sb.append(", textDirection=");
        sb.append((Object) y01.a(ck0Var.b));
        sb.append(", lineHeight=");
        sb.append((Object) t11.e(ck0Var.c));
        sb.append(", textIndent=");
        sb.append(ck0Var.d);
        sb.append(", platformStyle=");
        sb.append(this.c);
        sb.append(", lineHeightStyle=");
        sb.append(ck0Var.f);
        sb.append(", lineBreak=");
        sb.append((Object) s80.a(ck0Var.g));
        sb.append(", hyphens=");
        sb.append((Object) yy.a(ck0Var.h));
        sb.append(", textMotion=");
        sb.append(ck0Var.i);
        sb.append(')');
        return sb.toString();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public r11(defpackage.ux0 r3, defpackage.ck0 r4) {
        /*
            r2 = this;
            r3.getClass()
            mm0 r0 = r4.e
            if (r0 != 0) goto L9
            r0 = 0
            goto Lf
        L9:
            nm0 r1 = new nm0
            r1.<init>(r0)
            r0 = r1
        Lf:
            r2.<init>(r3, r4, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.r11.<init>(ux0, ck0):void");
    }

    public r11(ux0 ux0Var, ck0 ck0Var, nm0 nm0Var) {
        this.a = ux0Var;
        this.b = ck0Var;
        this.c = nm0Var;
    }
}
