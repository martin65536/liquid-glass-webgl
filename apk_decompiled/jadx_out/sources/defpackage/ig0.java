package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ig0 extends qg0 {
    public final bd0 c;
    public final pu d;
    public final kb0 e;
    public ng0 f;
    public pm0 g;
    public boolean h;
    public boolean i;
    public boolean j;

    /* JADX WARN: Type inference failed for: r3v1, types: [pu, java.lang.Object] */
    public ig0(bd0 bd0Var) {
        this.c = bd0Var;
        ?? obj = new Object();
        obj.b = new long[2];
        this.d = obj;
        this.e = new kb0(2);
        this.i = true;
        this.j = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v0, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v39 */
    /* JADX WARN: Type inference failed for: r5v40, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v41, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v42 */
    /* JADX WARN: Type inference failed for: r5v43 */
    /* JADX WARN: Type inference failed for: r5v44 */
    /* JADX WARN: Type inference failed for: r5v45 */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v47 */
    /* JADX WARN: Type inference failed for: r5v48 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9, types: [int] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v20, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    @Override // defpackage.qg0
    public final boolean a(kb0 kb0Var, l40 l40Var, c4 c4Var, boolean z) {
        pu puVar;
        kb0 kb0Var2;
        Object obj;
        boolean z2;
        boolean z3;
        pm0 pm0Var;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        int i;
        int i2;
        boolean z8;
        int i3;
        boolean z9;
        int i4;
        List list;
        List list2;
        int i5;
        um0 um0Var;
        l40 l40Var2 = l40Var;
        boolean a = super.a(kb0Var, l40Var, c4Var, z);
        jm jmVar = this.c;
        boolean z10 = true;
        if (jmVar.r) {
            ?? r8 = 0;
            while (jmVar != 0) {
                if (jmVar instanceof xm0) {
                    this.f = k81.B((xm0) jmVar, 16);
                } else if ((jmVar.g & 16) != 0 && (jmVar instanceof jm)) {
                    bd0 bd0Var = jmVar.t;
                    int i6 = 0;
                    jmVar = jmVar;
                    r8 = r8;
                    while (bd0Var != null) {
                        if ((bd0Var.g & 16) != 0) {
                            i6++;
                            r8 = r8;
                            if (i6 == 1) {
                                jmVar = bd0Var;
                            } else {
                                if (r8 == 0) {
                                    r8 = new ef0(new bd0[16]);
                                }
                                if (jmVar != 0) {
                                    r8.b(jmVar);
                                    jmVar = 0;
                                }
                                r8.b(bd0Var);
                            }
                        }
                        bd0Var = bd0Var.j;
                        jmVar = jmVar;
                        r8 = r8;
                    }
                    if (i6 == 1) {
                    }
                }
                jmVar = k81.h(r8);
            }
            if (this.f != null) {
                int d = kb0Var.d();
                int i7 = 0;
                while (true) {
                    puVar = this.d;
                    kb0Var2 = this.e;
                    if (i7 >= d) {
                        break;
                    }
                    long a2 = kb0Var.a(i7);
                    um0 um0Var2 = (um0) kb0Var.e(i7);
                    if (puVar.g(a2)) {
                        boolean z11 = z10;
                        long j = um0Var2.g;
                        List list3 = um0Var2.m;
                        long j2 = um0Var2.c;
                        if ((((j & 9223372034707292159L) + 36028792732385279L) & (-9223372034707292160L)) == 0 && (((j2 & 9223372034707292159L) + 36028792732385279L) & (-9223372034707292160L)) == 0) {
                            z9 = z11;
                            List list4 = er.e;
                            if (list3 == null) {
                                list = list4;
                            } else {
                                list = list3;
                            }
                            z8 = a;
                            ArrayList arrayList = new ArrayList(list.size());
                            if (list3 == null) {
                                list2 = list4;
                            } else {
                                list2 = list3;
                            }
                            i3 = d;
                            int size = list2.size();
                            i4 = i7;
                            int i8 = 0;
                            while (i8 < size) {
                                List list5 = list2;
                                ly lyVar = (ly) list2.get(i8);
                                kb0 kb0Var3 = kb0Var2;
                                long j3 = a2;
                                long j4 = lyVar.b;
                                if ((((j4 & 9223372034707292159L) + 36028792732385279L) & (-9223372034707292160L)) == 0) {
                                    um0Var = um0Var2;
                                    long j5 = lyVar.a;
                                    i5 = size;
                                    ng0 ng0Var = this.f;
                                    ng0Var.getClass();
                                    arrayList.add(new ly(j5, ng0Var.R(l40Var2, j4), lyVar.c, lyVar.d, lyVar.e));
                                } else {
                                    i5 = size;
                                    um0Var = um0Var2;
                                }
                                i8++;
                                size = i5;
                                list2 = list5;
                                kb0Var2 = kb0Var3;
                                a2 = j3;
                                um0Var2 = um0Var;
                            }
                            kb0 kb0Var4 = kb0Var2;
                            long j6 = a2;
                            ng0 ng0Var2 = this.f;
                            ng0Var2.getClass();
                            long R = ng0Var2.R(l40Var2, j);
                            ng0 ng0Var3 = this.f;
                            ng0Var3.getClass();
                            um0 um0Var3 = new um0(um0Var2.a, um0Var2.b, ng0Var3.R(l40Var2, j2), um0Var2.d, um0Var2.e, um0Var2.f, R, um0Var2.h, um0Var2.i, arrayList, um0Var2.j, um0Var2.k, um0Var2.l, um0Var2.n);
                            um0 um0Var4 = um0Var2.q;
                            if (um0Var4 == null) {
                                um0Var4 = um0Var2;
                            }
                            um0Var3.q = um0Var4;
                            um0 um0Var5 = um0Var2.q;
                            if (um0Var5 != null) {
                                um0Var2 = um0Var5;
                            }
                            um0Var3.q = um0Var2;
                            kb0Var4.b(j6, um0Var3);
                        } else {
                            z8 = a;
                            i3 = d;
                            i4 = i7;
                            z9 = z11;
                        }
                    } else {
                        z8 = a;
                        i3 = d;
                        z9 = z10;
                        i4 = i7;
                    }
                    i7 = i4 + 1;
                    l40Var2 = l40Var;
                    d = i3;
                    z10 = z9;
                    a = z8;
                }
                boolean z12 = a;
                boolean z13 = z10;
                if (kb0Var2.d() == 0) {
                    puVar.a = 0;
                    this.a.g();
                    return z13;
                }
                int i9 = puVar.a;
                while (true) {
                    i9--;
                    if (-1 >= i9) {
                        break;
                    }
                    long j7 = ((long[]) puVar.b)[i9];
                    if (kb0Var.e) {
                        int i10 = kb0Var.h;
                        long[] jArr = kb0Var.f;
                        Object[] objArr = kb0Var.g;
                        int i11 = 0;
                        for (int i12 = 0; i12 < i10; i12++) {
                            Object obj2 = objArr[i12];
                            if (obj2 != jc0.g) {
                                if (i12 != i11) {
                                    jArr[i11] = jArr[i12];
                                    objArr[i11] = obj2;
                                    objArr[i12] = null;
                                }
                                i11++;
                            }
                        }
                        kb0Var.e = false;
                        kb0Var.h = i11;
                    }
                    if (o4.n(kb0Var.f, kb0Var.h, j7) < 0 && i9 < (i2 = puVar.a)) {
                        int i13 = i2 - 1;
                        int i14 = i9;
                        while (i14 < i13) {
                            long[] jArr2 = (long[]) puVar.b;
                            int i15 = i14 + 1;
                            jArr2[i14] = jArr2[i15];
                            i14 = i15;
                        }
                        puVar.a--;
                    }
                }
                ArrayList arrayList2 = new ArrayList(kb0Var2.d());
                int d2 = kb0Var2.d();
                for (int i16 = 0; i16 < d2; i16++) {
                    arrayList2.add(kb0Var2.e(i16));
                }
                pm0 pm0Var2 = new pm0(arrayList2, c4Var);
                int size2 = arrayList2.size();
                int i17 = 0;
                while (true) {
                    if (i17 < size2) {
                        obj = arrayList2.get(i17);
                        if (c4Var.l(((um0) obj).a)) {
                            break;
                        }
                        i17++;
                    } else {
                        obj = null;
                        break;
                    }
                }
                um0 um0Var6 = (um0) obj;
                if (um0Var6 != null) {
                    boolean z14 = um0Var6.d;
                    if (!z) {
                        z2 = false;
                        this.i = false;
                    } else {
                        z2 = false;
                        if (!this.i && (z14 || um0Var6.h)) {
                            ng0 ng0Var4 = this.f;
                            ng0Var4.getClass();
                            long j8 = ng0Var4.g;
                            long j9 = um0Var6.c;
                            float intBitsToFloat = Float.intBitsToFloat((int) (j9 >> 32));
                            float intBitsToFloat2 = Float.intBitsToFloat((int) (j9 & 4294967295L));
                            int i18 = (int) (j8 >> 32);
                            int i19 = (int) (j8 & 4294967295L);
                            if (intBitsToFloat < 0.0f) {
                                z4 = z13;
                            } else {
                                z4 = false;
                            }
                            if (intBitsToFloat > i18) {
                                z5 = z13;
                            } else {
                                z5 = false;
                            }
                            boolean z15 = z5 | z4;
                            if (intBitsToFloat2 < 0.0f) {
                                z6 = z13;
                            } else {
                                z6 = false;
                            }
                            boolean z16 = z6 | z15;
                            if (intBitsToFloat2 > i19) {
                                z7 = z13;
                            } else {
                                z7 = false;
                            }
                            this.i = !(z7 | z16);
                        }
                    }
                    boolean z17 = this.i;
                    boolean z18 = this.h;
                    int i20 = 5;
                    if (z17 != z18 && ((i = pm0Var2.d) == 3 || i == 4 || i == 5)) {
                        if (z17) {
                            i20 = 4;
                        }
                        pm0Var2.d = i20;
                    } else {
                        int i21 = pm0Var2.d;
                        if (i21 == 4 && z18 && !this.j) {
                            pm0Var2.d = 3;
                        } else if (i21 == 5 && z17 && z14) {
                            pm0Var2.d = 3;
                        }
                    }
                } else {
                    z2 = false;
                }
                if (!z12 && pm0Var2.d == 3 && (pm0Var = this.g) != null) {
                    ?? r1 = pm0Var.a;
                    int size3 = r1.size();
                    ?? r4 = pm0Var2.a;
                    if (size3 == r4.size()) {
                        int size4 = r4.size();
                        for (?? r5 = z2; r5 < size4; r5++) {
                            if (ch0.c(((um0) r1.get(r5)).c, ((um0) r4.get(r5)).c)) {
                            }
                        }
                        z3 = z2;
                        this.g = pm0Var2;
                        return z3;
                    }
                }
                z3 = z13;
                this.g = pm0Var2;
                return z3;
            }
        }
        return true;
    }

    @Override // defpackage.qg0
    public final void b(c4 c4Var) {
        super.b(c4Var);
        pm0 pm0Var = this.g;
        if (pm0Var == null) {
            return;
        }
        this.h = this.i;
        List list = pm0Var.a;
        int size = list.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            um0 um0Var = (um0) list.get(i);
            boolean z2 = um0Var.d;
            long j = um0Var.a;
            boolean l = c4Var.l(j);
            boolean z3 = this.i;
            if ((!z2 && !l) || (!z2 && !z3)) {
                this.d.h(j);
            }
        }
        this.i = false;
        if (pm0Var.d == 5) {
            z = true;
        }
        this.j = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [ef0] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [ef0] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r8v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v2, types: [bd0] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [bd0] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final void c() {
        ef0 ef0Var = this.a;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            ((ig0) objArr[i2]).c();
        }
        jm jmVar = this.c;
        ?? r1 = 0;
        while (jmVar != 0) {
            if (jmVar instanceof xm0) {
                ((xm0) jmVar).g0();
            } else if ((jmVar.g & 16) != 0 && (jmVar instanceof jm)) {
                bd0 bd0Var = jmVar.t;
                int i3 = 0;
                r1 = r1;
                jmVar = jmVar;
                while (bd0Var != null) {
                    if ((bd0Var.g & 16) != 0) {
                        i3++;
                        r1 = r1;
                        if (i3 == 1) {
                            jmVar = bd0Var;
                        } else {
                            if (r1 == 0) {
                                r1 = new ef0(new bd0[16]);
                            }
                            if (jmVar != 0) {
                                r1.b(jmVar);
                                jmVar = 0;
                            }
                            r1.b(bd0Var);
                        }
                    }
                    bd0Var = bd0Var.j;
                    r1 = r1;
                    jmVar = jmVar;
                }
                if (i3 == 1) {
                }
            }
            jmVar = k81.h(r1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:6:0x009e A[LOOP:0: B:5:0x009c->B:6:0x009e, LOOP_END] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [bd0] */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean d(defpackage.c4 r15) {
        /*
            r14 = this;
            kb0 r0 = r14.e
            int r1 = r0.d()
            r2 = 0
            r3 = 0
            if (r1 != 0) goto Ld
        La:
            r9 = r3
            goto L94
        Ld:
            bd0 r1 = r14.c
            boolean r4 = r1.r
            if (r4 != 0) goto L14
            goto La
        L14:
            ng0 r4 = r1.l
            if (r4 == 0) goto L21
            z40 r4 = r4.s
            if (r4 == 0) goto L21
            boolean r4 = r4.F()
            goto L22
        L21:
            r4 = r3
        L22:
            if (r4 != 0) goto L25
            goto La
        L25:
            pm0 r4 = r14.g
            r4.getClass()
            ng0 r5 = r14.f
            r5.getClass()
            long r5 = r5.g
            r7 = r1
            r8 = r2
        L33:
            r9 = 1
            if (r7 == 0) goto L7d
            boolean r10 = r7 instanceof defpackage.xm0
            if (r10 == 0) goto L42
            xm0 r7 = (defpackage.xm0) r7
            qm0 r9 = defpackage.qm0.g
            r7.N(r4, r9, r5)
            goto L78
        L42:
            int r10 = r7.g
            r11 = 16
            r10 = r10 & r11
            if (r10 == 0) goto L78
            boolean r10 = r7 instanceof defpackage.jm
            if (r10 == 0) goto L78
            r10 = r7
            jm r10 = (defpackage.jm) r10
            bd0 r10 = r10.t
            r12 = r3
        L53:
            if (r10 == 0) goto L75
            int r13 = r10.g
            r13 = r13 & r11
            if (r13 == 0) goto L72
            int r12 = r12 + 1
            if (r12 != r9) goto L60
            r7 = r10
            goto L72
        L60:
            if (r8 != 0) goto L69
            ef0 r8 = new ef0
            bd0[] r13 = new defpackage.bd0[r11]
            r8.<init>(r13)
        L69:
            if (r7 == 0) goto L6f
            r8.b(r7)
            r7 = r2
        L6f:
            r8.b(r10)
        L72:
            bd0 r10 = r10.j
            goto L53
        L75:
            if (r12 != r9) goto L78
            goto L33
        L78:
            bd0 r7 = defpackage.k81.h(r8)
            goto L33
        L7d:
            boolean r1 = r1.r
            if (r1 == 0) goto L94
            ef0 r1 = r14.a
            java.lang.Object[] r4 = r1.e
            int r1 = r1.g
            r5 = r3
        L88:
            if (r5 >= r1) goto L94
            r6 = r4[r5]
            ig0 r6 = (defpackage.ig0) r6
            r6.d(r15)
            int r5 = r5 + 1
            goto L88
        L94:
            r14.b(r15)
            int r15 = r0.h
            java.lang.Object[] r1 = r0.g
            r4 = r3
        L9c:
            if (r4 >= r15) goto La3
            r1[r4] = r2
            int r4 = r4 + 1
            goto L9c
        La3:
            r0.h = r3
            r0.e = r3
            r14.f = r2
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ig0.d(c4):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v2, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [ef0] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v10, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v9 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [ef0] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7, types: [ef0] */
    /* JADX WARN: Type inference failed for: r7v9 */
    public final boolean e(c4 c4Var, boolean z) {
        boolean z2;
        z40 z40Var;
        if (this.e.d() == 0) {
            return false;
        }
        jm jmVar = this.c;
        if (jmVar.r) {
            ng0 ng0Var = jmVar.l;
            if (ng0Var != null && (z40Var = ng0Var.s) != null) {
                z2 = z40Var.F();
            } else {
                z2 = false;
            }
            if (z2) {
                pm0 pm0Var = this.g;
                pm0Var.getClass();
                ng0 ng0Var2 = this.f;
                ng0Var2.getClass();
                long j = ng0Var2.g;
                jm jmVar2 = jmVar;
                ?? r7 = 0;
                while (jmVar2 != 0) {
                    if (jmVar2 instanceof xm0) {
                        ((xm0) jmVar2).N(pm0Var, qm0.e, j);
                    } else if ((jmVar2.g & 16) != 0 && (jmVar2 instanceof jm)) {
                        bd0 bd0Var = jmVar2.t;
                        int i = 0;
                        jmVar2 = jmVar2;
                        r7 = r7;
                        while (bd0Var != null) {
                            if ((bd0Var.g & 16) != 0) {
                                i++;
                                r7 = r7;
                                if (i == 1) {
                                    jmVar2 = bd0Var;
                                } else {
                                    if (r7 == 0) {
                                        r7 = new ef0(new bd0[16]);
                                    }
                                    if (jmVar2 != 0) {
                                        r7.b(jmVar2);
                                        jmVar2 = 0;
                                    }
                                    r7.b(bd0Var);
                                }
                            }
                            bd0Var = bd0Var.j;
                            jmVar2 = jmVar2;
                            r7 = r7;
                        }
                        if (i == 1) {
                        }
                    }
                    jmVar2 = k81.h(r7);
                }
                if (jmVar.r) {
                    ef0 ef0Var = this.a;
                    Object[] objArr = ef0Var.e;
                    int i2 = ef0Var.g;
                    for (int i3 = 0; i3 < i2; i3++) {
                        ig0 ig0Var = (ig0) objArr[i3];
                        this.f.getClass();
                        ig0Var.e(c4Var, z);
                    }
                }
                if (jmVar.r) {
                    ?? r13 = 0;
                    while (jmVar != 0) {
                        if (jmVar instanceof xm0) {
                            ((xm0) jmVar).N(pm0Var, qm0.f, j);
                        } else if ((jmVar.g & 16) != 0 && (jmVar instanceof jm)) {
                            bd0 bd0Var2 = jmVar.t;
                            int i4 = 0;
                            jmVar = jmVar;
                            r13 = r13;
                            while (bd0Var2 != null) {
                                if ((bd0Var2.g & 16) != 0) {
                                    i4++;
                                    r13 = r13;
                                    if (i4 == 1) {
                                        jmVar = bd0Var2;
                                    } else {
                                        if (r13 == 0) {
                                            r13 = new ef0(new bd0[16]);
                                        }
                                        if (jmVar != 0) {
                                            r13.b(jmVar);
                                            jmVar = 0;
                                        }
                                        r13.b(bd0Var2);
                                    }
                                }
                                bd0Var2 = bd0Var2.j;
                                jmVar = jmVar;
                                r13 = r13;
                            }
                            if (i4 == 1) {
                            }
                        }
                        jmVar = k81.h(r13);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final void f(long j, pe0 pe0Var) {
        pu puVar = this.d;
        if (puVar.g(j) && pe0Var.g(this) < 0) {
            puVar.h(j);
            this.e.c(j);
        }
        ef0 ef0Var = this.a;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            ((ig0) objArr[i2]).f(j, pe0Var);
        }
    }

    public final String toString() {
        return "Node(modifierNode=" + this.c + ", children=" + this.a + ", pointerIds=" + this.d + ')';
    }
}
