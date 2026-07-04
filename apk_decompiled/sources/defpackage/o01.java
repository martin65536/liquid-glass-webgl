package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class o01 {
    public static final hp a = new hp(3, null, 2);

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0049 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0052  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0047 -> B:10:0x004a). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object a(defpackage.xz0 r5, boolean r6, defpackage.qm0 r7, defpackage.s9 r8) {
        /*
            boolean r0 = r8 instanceof defpackage.f01
            if (r0 == 0) goto L13
            r0 = r8
            f01 r0 = (defpackage.f01) r0
            int r1 = r0.l
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.l = r1
            goto L18
        L13:
            f01 r0 = new f01
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.k
            int r1 = r0.l
            r2 = 1
            if (r1 == 0) goto L36
            if (r1 != r2) goto L2f
            boolean r5 = r0.j
            qm0 r6 = r0.i
            xz0 r7 = r0.h
            defpackage.o30.x(r8)
            r4 = r6
            r6 = r5
            r5 = r7
            r7 = r4
            goto L4a
        L2f:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r5)
            r5 = 0
            return r5
        L36:
            defpackage.o30.x(r8)
        L39:
            r0.h = r5
            r0.i = r7
            r0.j = r6
            r0.l = r2
            java.lang.Object r8 = r5.A(r7, r0)
            ik r1 = defpackage.ik.e
            if (r8 != r1) goto L4a
            return r1
        L4a:
            pm0 r8 = (defpackage.pm0) r8
            boolean r1 = d(r8, r6)
            if (r1 == 0) goto L39
            java.util.List r5 = r8.a
            r6 = 0
            java.lang.Object r5 = r5.get(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o01.a(xz0, boolean, qm0, s9):java.lang.Object");
    }

    public static /* synthetic */ Object b(xz0 xz0Var, s9 s9Var, int i) {
        boolean z = true;
        if ((i & 1) == 0) {
            z = false;
        }
        return a(xz0Var, z, qm0.f, s9Var);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x004a A[LOOP:0: B:11:0x0048->B:12:0x004a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x003b -> B:10:0x003e). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object c(defpackage.xz0 r7, defpackage.jj r8) {
        /*
            boolean r0 = r8 instanceof defpackage.h01
            if (r0 == 0) goto L13
            r0 = r8
            h01 r0 = (defpackage.h01) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            h01 r0 = new h01
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.i
            int r1 = r0.j
            r2 = 1
            if (r1 == 0) goto L2e
            if (r1 != r2) goto L27
            xz0 r7 = r0.h
            defpackage.o30.x(r8)
            goto L3e
        L27:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            r7 = 0
            return r7
        L2e:
            defpackage.o30.x(r8)
        L31:
            r0.h = r7
            r0.j = r2
            java.lang.Object r8 = defpackage.d3.m(r7, r0)
            ik r1 = defpackage.ik.e
            if (r8 != r1) goto L3e
            return r1
        L3e:
            pm0 r8 = (defpackage.pm0) r8
            java.util.List r1 = r8.a
            int r3 = r1.size()
            r4 = 0
            r5 = r4
        L48:
            if (r5 >= r3) goto L56
            java.lang.Object r6 = r1.get(r5)
            um0 r6 = (defpackage.um0) r6
            r6.a()
            int r5 = r5 + 1
            goto L48
        L56:
            java.util.List r8 = r8.a
            int r1 = r8.size()
        L5c:
            if (r4 >= r1) goto L6c
            java.lang.Object r3 = r8.get(r4)
            um0 r3 = (defpackage.um0) r3
            boolean r3 = r3.d
            if (r3 == 0) goto L69
            goto L31
        L69:
            int r4 = r4 + 1
            goto L5c
        L6c:
            x31 r7 = defpackage.x31.a
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o01.c(xz0, jj):java.lang.Object");
    }

    public static boolean d(pm0 pm0Var, boolean z) {
        List list = pm0Var.a;
        int size = list.size();
        int i = 0;
        while (true) {
            boolean z2 = true;
            if (i >= size) {
                return true;
            }
            um0 um0Var = (um0) list.get(i);
            if (z) {
                if (um0Var.b() || um0Var.h || !um0Var.d) {
                    z2 = false;
                }
            } else {
                z2 = g30.l(um0Var);
            }
            if (!z2) {
                return false;
            }
            i++;
        }
    }

    public static dy0 e(hk hkVar, d30 d30Var, kv kvVar) {
        return f31.G(hkVar, null, new f(d30Var, kvVar, null, 15), 1);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x002b. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01b5  */
    /* JADX WARN: Type inference failed for: r0v5, types: [gv] */
    /* JADX WARN: Type inference failed for: r13v1, types: [ij] */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v14, types: [java.lang.Object, gv] */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v24 */
    /* JADX WARN: Type inference failed for: r13v5, types: [java.lang.Object, gv] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v9, types: [yj, ij, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v2, types: [jj, s9, j01] */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r7v12, types: [java.lang.Object, xz0] */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object f(defpackage.xz0 r20, defpackage.hk r21, defpackage.mn0 r22, defpackage.lv r23, defpackage.x90 r24, defpackage.s9 r25) {
        /*
            Method dump skipped, instructions count: 1022
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o01.f(xz0, hk, mn0, lv, x90, s9):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /* JADX WARN: Type inference failed for: r9v2, types: [ep0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object g(defpackage.xz0 r7, defpackage.qm0 r8, defpackage.jj r9) {
        /*
            boolean r0 = r9 instanceof defpackage.m01
            if (r0 == 0) goto L13
            r0 = r9
            m01 r0 = (defpackage.m01) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            m01 r0 = new m01
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.i
            int r1 = r0.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L2e
            if (r1 != r3) goto L28
            ep0 r7 = r0.h
            defpackage.o30.x(r9)     // Catch: defpackage.rm0 -> L59
            goto L56
        L28:
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r7)
            return r2
        L2e:
            defpackage.o30.x(r9)
            ep0 r9 = new ep0
            r9.<init>()
            fb0 r1 = defpackage.fb0.a
            r9.e = r1
            l51 r1 = r7.E()     // Catch: defpackage.rm0 -> L59
            long r4 = r1.c()     // Catch: defpackage.rm0 -> L59
            su r1 = new su     // Catch: defpackage.rm0 -> L59
            r6 = 2
            r1.<init>(r8, r9, r2, r6)     // Catch: defpackage.rm0 -> L59
            r0.h = r9     // Catch: defpackage.rm0 -> L59
            r0.j = r3     // Catch: defpackage.rm0 -> L59
            java.lang.Object r7 = r7.H(r4, r1, r0)     // Catch: defpackage.rm0 -> L59
            ik r8 = defpackage.ik.e
            if (r7 != r8) goto L55
            return r8
        L55:
            r7 = r9
        L56:
            java.lang.Object r7 = r7.e
            return r7
        L59:
            hb0 r7 = defpackage.hb0.a
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o01.g(xz0, qm0, jj):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00c7, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ad, code lost:
    
        if (r0 == r7) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00ad -> B:11:0x0031). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object h(defpackage.xz0 r17, defpackage.qm0 r18, defpackage.jj r19) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o01.h(xz0, qm0, jj):java.lang.Object");
    }
}
