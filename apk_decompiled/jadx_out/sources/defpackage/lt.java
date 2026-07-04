package defpackage;

import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lt implements ht {
    public final b4 a;
    public final b4 b;
    public final ft d;
    public me0 f;
    public pt h;
    public final pt c = new pt(2, null, 14);
    public final jt e = new jt(this);
    public final pe0 g = new pe0(1);

    public lt(b4 b4Var, b4 b4Var2) {
        this.a = b4Var;
        this.b = b4Var2;
        this.d = new ft(this, b4Var2);
    }

    public final boolean a(boolean z) {
        lg0 lg0Var;
        if (f() != null) {
            pt f = f();
            h(null);
            if (f != null) {
                ot otVar = ot.e;
                ot otVar2 = ot.g;
                f.E0(otVar, otVar2);
                if (!f.e.r) {
                    q00.b("visitAncestors called on an unattached node");
                }
                bd0 bd0Var = f.e.i;
                z40 E = k81.E(f);
                while (E != null) {
                    if ((E.H.f.h & 1024) != 0) {
                        while (bd0Var != null) {
                            if ((bd0Var.g & 1024) != 0) {
                                bd0 bd0Var2 = bd0Var;
                                ef0 ef0Var = null;
                                while (bd0Var2 != null) {
                                    if (bd0Var2 instanceof pt) {
                                        ((pt) bd0Var2).E0(ot.f, otVar2);
                                    } else if ((bd0Var2.g & 1024) != 0 && (bd0Var2 instanceof jm)) {
                                        int i = 0;
                                        for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                            if ((bd0Var3.g & 1024) != 0) {
                                                i++;
                                                if (i == 1) {
                                                    bd0Var2 = bd0Var3;
                                                } else {
                                                    if (ef0Var == null) {
                                                        ef0Var = new ef0(new bd0[16]);
                                                    }
                                                    if (bd0Var2 != null) {
                                                        ef0Var.b(bd0Var2);
                                                        bd0Var2 = null;
                                                    }
                                                    ef0Var.b(bd0Var3);
                                                }
                                            }
                                        }
                                        if (i == 1) {
                                        }
                                    }
                                    bd0Var2 = k81.h(ef0Var);
                                }
                            }
                            bd0Var = bd0Var.i;
                        }
                    }
                    E = E.s();
                    if (E != null && (lg0Var = E.H) != null) {
                        bd0Var = lg0Var.e;
                    } else {
                        bd0Var = null;
                    }
                }
            }
        }
        return true;
    }

    public final boolean b(int i, boolean z, boolean z2) {
        boolean z3 = true;
        if (!z) {
            int ordinal = dl.G(this.c).ordinal();
            if (ordinal != 0) {
                if (ordinal != 1 && ordinal != 2 && ordinal != 3) {
                    v7.k();
                    return false;
                }
                z3 = false;
            } else {
                a(z);
            }
        } else {
            a(z);
        }
        if (z3 && z2) {
            c();
        }
        return z3;
    }

    public final void c() {
        b4 b4Var = this.a;
        if (!b4Var.isFocused() && !b4Var.hasFocus()) {
            if (b4Var.hasFocus()) {
                View findFocus = b4Var.findFocus();
                if (findFocus != null) {
                    findFocus.clearFocus();
                }
                b4Var.clearFocus();
                return;
            }
            return;
        }
        b4Var.clearFocus();
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0057, code lost:
    
        if (r7 == null) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0167 A[Catch: all -> 0x02d2, TryCatch #0 {all -> 0x02d2, blocks: (B:3:0x0007, B:5:0x000e, B:9:0x0019, B:13:0x0023, B:16:0x002f, B:18:0x0035, B:19:0x003a, B:21:0x0042, B:23:0x0047, B:25:0x004d, B:29:0x0053, B:34:0x0167, B:36:0x016d, B:37:0x0170, B:39:0x017b, B:42:0x0187, B:46:0x0191, B:49:0x0197, B:50:0x019c, B:52:0x01a4, B:54:0x01aa, B:56:0x01ae, B:58:0x01b6, B:60:0x01bc, B:66:0x01c4, B:68:0x01cd, B:69:0x01d1, B:64:0x01d4, B:75:0x01da, B:86:0x01df, B:89:0x01e2, B:91:0x01e8, B:98:0x01ec, B:103:0x01f3, B:105:0x01fb, B:110:0x020b, B:112:0x0210, B:146:0x0214, B:141:0x024d, B:114:0x0217, B:116:0x021d, B:118:0x0221, B:120:0x0229, B:122:0x022f, B:128:0x0237, B:130:0x0240, B:131:0x0244, B:126:0x0247, B:148:0x0252, B:152:0x0262, B:154:0x0267, B:188:0x026b, B:183:0x02ad, B:156:0x0277, B:158:0x027d, B:160:0x0281, B:162:0x0289, B:164:0x028f, B:170:0x0297, B:172:0x02a0, B:173:0x02a4, B:168:0x02a7, B:195:0x02b4, B:197:0x02bb, B:210:0x005b, B:212:0x0061, B:213:0x0064, B:215:0x006c, B:218:0x0078, B:222:0x0082, B:257:0x00d5, B:259:0x00d9, B:224:0x0087, B:226:0x008d, B:228:0x0091, B:230:0x0099, B:232:0x009f, B:238:0x00a7, B:240:0x00b0, B:241:0x00b4, B:236:0x00b7, B:247:0x00bd, B:261:0x00c2, B:264:0x00c5, B:266:0x00cb, B:273:0x00cf, B:278:0x00df, B:280:0x00e5, B:281:0x00e8, B:283:0x00f2, B:286:0x00fe, B:290:0x0108, B:325:0x015b, B:327:0x015f, B:292:0x010d, B:294:0x0113, B:296:0x0117, B:298:0x011f, B:300:0x0125, B:306:0x012d, B:308:0x0136, B:309:0x013a, B:304:0x013d, B:315:0x0143, B:330:0x0148, B:333:0x014b, B:335:0x0151, B:342:0x0155), top: B:2:0x0007 }] */
    /* JADX WARN: Type inference failed for: r0v20, types: [ef0] */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24, types: [ef0] */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v31 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r12v23, types: [bd0] */
    /* JADX WARN: Type inference failed for: r12v24, types: [bd0] */
    /* JADX WARN: Type inference failed for: r12v28, types: [bd0] */
    /* JADX WARN: Type inference failed for: r12v29, types: [bd0] */
    /* JADX WARN: Type inference failed for: r12v33, types: [bd0] */
    /* JADX WARN: Type inference failed for: r12v34 */
    /* JADX WARN: Type inference failed for: r12v35, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v36 */
    /* JADX WARN: Type inference failed for: r12v37 */
    /* JADX WARN: Type inference failed for: r12v38 */
    /* JADX WARN: Type inference failed for: r12v39 */
    /* JADX WARN: Type inference failed for: r12v41, types: [bd0] */
    /* JADX WARN: Type inference failed for: r12v42 */
    /* JADX WARN: Type inference failed for: r12v43, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v44 */
    /* JADX WARN: Type inference failed for: r12v45 */
    /* JADX WARN: Type inference failed for: r12v46 */
    /* JADX WARN: Type inference failed for: r12v47 */
    /* JADX WARN: Type inference failed for: r12v60 */
    /* JADX WARN: Type inference failed for: r12v61 */
    /* JADX WARN: Type inference failed for: r12v62 */
    /* JADX WARN: Type inference failed for: r12v63 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v10, types: [ef0] */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r14v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean d(android.view.KeyEvent r13, defpackage.vu r14) {
        /*
            Method dump skipped, instructions count: 727
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lt.d(android.view.KeyEvent, vu):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x010e, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Boolean e(int r20, defpackage.wo0 r21, defpackage.gv r22) {
        /*
            Method dump skipped, instructions count: 719
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lt.e(int, wo0, gv):java.lang.Boolean");
    }

    public final pt f() {
        pt ptVar = this.h;
        if (ptVar != null && ptVar.r) {
            return ptVar;
        }
        return null;
    }

    public final boolean g(int i) {
        boolean z = false;
        if (!b(i, false, false)) {
            return false;
        }
        Boolean e = e(i, null, new y3(i, 2));
        if (e != null) {
            z = e.booleanValue();
        }
        if (!z) {
            c();
        }
        return z;
    }

    public final void h(pt ptVar) {
        pt ptVar2 = this.h;
        this.h = ptVar;
        pe0 pe0Var = this.g;
        Object[] objArr = pe0Var.a;
        int i = pe0Var.b;
        for (int i2 = 0; i2 < i; i2++) {
            ((gt) objArr[i2]).g(ptVar2, ptVar);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0352, code lost:
    
        r10 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a3, code lost:
    
        r30 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00ad, code lost:
    
        if (((r10 & ((~r10) << 6)) & (-9187201950435737472L)) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00af, code lost:
    
        r0 = r4.b(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00b5, code lost:
    
        if (r4.e != 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c6, code lost:
    
        if (((r4.a[r0 >> 3] >> ((r0 & 7) << 3)) & 255) != 254) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00d0, code lost:
    
        r0 = r4.c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00d2, code lost:
    
        if (r0 <= r5) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d4, code lost:
    
        r11 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00eb, code lost:
    
        if (java.lang.Long.compare((r4.d * 32) ^ Long.MIN_VALUE, (r0 * 25) ^ Long.MIN_VALUE) > 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ed, code lost:
    
        r0 = r4.a;
        r3 = r4.c;
        r5 = r4.b;
        r6 = (r3 + 7) >> 3;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00f9, code lost:
    
        if (r14 >= r6) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00fb, code lost:
    
        r9 = r0[r14] & (-9187201950435737472L);
        r0[r14] = (-72340172838076674L) & ((~r9) + (r9 >>> 7));
        r14 = r14 + 1;
        r11 = r11;
        r12 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0116, code lost:
    
        r36 = r12;
        r40 = 128;
        r13 = r11;
        r6 = defpackage.i8.T(r0);
        r9 = r6 - 1;
        r14 = 72057594037927935L;
        r0[r9] = (r0[r9] & 72057594037927935L) | (-72057594037927936L);
        r0[r6] = r0[0];
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0135, code lost:
    
        if (r6 == r3) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0137, code lost:
    
        r9 = r6 >> 3;
        r12 = (r6 & 7) << 3;
        r10 = (r0[r9] >> r12) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0144, code lost:
    
        if (r10 != 128) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x014b, code lost:
    
        if (r10 == 254) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x014e, code lost:
    
        r10 = r5[r6];
        r10 = ((int) (r10 ^ (r10 >>> r30))) * r31;
        r11 = (r10 ^ (r10 << 16)) >>> 7;
        r23 = r4.b(r11);
        r11 = r11 & r3;
        r29 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x016e, code lost:
    
        if ((((r23 - r11) & r3) / 8) != (((r6 - r11) & r3) / 8)) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0191, code lost:
    
        r32 = r14;
        r11 = r23 >> 3;
        r13 = r0[r11];
        r15 = (r23 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01a1, code lost:
    
        if (((r13 >> r15) & 255) != 128) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01a3, code lost:
    
        r38 = r7;
        r39 = r8;
        r24 = r5;
        r25 = r6;
        r0[r11] = ((~(255 << r15)) & r13) | ((r10 & 127) << r15);
        r0[r9] = (r0[r9] & (~(255 << r12))) | (128 << r12);
        r24[r23] = r24[r25];
        r24[r25] = 0;
        r6 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01e7, code lost:
    
        r0[r0.length - 1] = (r0[0] & r32) | Long.MIN_VALUE;
        r6 = r6 + 1;
        r5 = r24;
        r13 = r29;
        r14 = r32;
        r7 = r38;
        r8 = r39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01ca, code lost:
    
        r24 = r5;
        r25 = r6;
        r38 = r7;
        r39 = r8;
        r0[r11] = ((r10 & 127) << r15) | ((~(255 << r15)) & r13);
        r5 = r24[r23];
        r24[r23] = r24[r25];
        r24[r25] = r5;
        r6 = r25 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0170, code lost:
    
        r32 = r14;
        r0[r9] = ((r10 & 127) << r12) | (r0[r9] & (~(255 << r12)));
        r0[r0.length - r7] = (r0[0] & r32) | Long.MIN_VALUE;
        r6 = r6 + 1;
        r13 = r29;
        r14 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0146, code lost:
    
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0200, code lost:
    
        r38 = r7;
        r39 = r8;
        r4.e = defpackage.zs0.a(r4.c) - r4.d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0285, code lost:
    
        r0 = r4.b(r39);
        r38 = r38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0289, code lost:
    
        r29 = r0;
        r4.d++;
        r0 = r4.e;
        r3 = r4.a;
        r5 = r29 >> 3;
        r6 = r3[r5];
        r8 = (r29 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x02a3, code lost:
    
        if (((r6 >> r8) & 255) != r40) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x02a5, code lost:
    
        r22 = r38 == true ? 1 : 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x02a7, code lost:
    
        r4.e = r0 - r22;
        r0 = r4.c;
        r6 = (r6 & (~(255 << r8))) | (r36 << r8);
        r3[r5] = r6;
        r3[(((r29 - 7) & r0) + (r0 & 7)) >> 3] = r6;
        r38 = r38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0213, code lost:
    
        r38 = 1;
        r39 = r8;
        r36 = r12;
        r40 = 128;
        r0 = defpackage.zs0.b(r4.c);
        r3 = r4.a;
        r5 = r4.b;
        r6 = r4.c;
        r4.c(r0);
        r0 = r4.a;
        r7 = r4.b;
        r8 = r4.c;
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0232, code lost:
    
        if (r9 >= r6) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0241, code lost:
    
        if (((r3[r9 >> 3] >> ((r9 & 7) << 3)) & 255) >= 128) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0243, code lost:
    
        r10 = r5[r9];
        r12 = ((int) ((r10 >>> r30) ^ r10)) * r31;
        r12 = r12 ^ (r12 << 16);
        r13 = r4.b(r12 >>> 7);
        r14 = r12 & 127;
        r12 = r13 >> 3;
        r16 = (r13 & 7) << 3;
        r19 = r5;
        r20 = r6;
        r5 = (r0[r12] & (~(255 << r16))) | (r14 << r16);
        r0[r12] = r5;
        r0[(((r13 - 7) & r8) + (r8 & 7)) >> 3] = r5;
        r7[r13] = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x027e, code lost:
    
        r9 = r9 + 1;
        r5 = r19;
        r6 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x027a, code lost:
    
        r19 = r5;
        r20 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00c8, code lost:
    
        r38 = 1;
        r36 = r12;
        r40 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0350, code lost:
    
        if (((r6 & ((~r6) << 6)) & (-9187201950435737472L)) == 0) goto L85;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean i(android.view.KeyEvent r41) {
        /*
            Method dump skipped, instructions count: 911
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lt.i(android.view.KeyEvent):boolean");
    }
}
