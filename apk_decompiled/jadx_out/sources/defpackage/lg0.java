package defpackage;

import java.util.HashSet;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lg0 {
    public final z40 a;
    public final kg0 b;
    public final w00 c;
    public ng0 d;
    public final e01 e;
    public bd0 f;
    public ef0 g;
    public ef0 h;
    public final ef0 i;
    public jg0 j;

    /* JADX WARN: Type inference failed for: r0v0, types: [kg0, bd0] */
    public lg0(z40 z40Var) {
        this.a = z40Var;
        ?? bd0Var = new bd0();
        bd0Var.h = -1;
        this.b = bd0Var;
        w00 w00Var = new w00(z40Var);
        this.c = w00Var;
        this.d = w00Var;
        e01 e01Var = w00Var.U;
        this.e = e01Var;
        this.f = e01Var;
        this.i = new ef0(new cd0[16]);
    }

    public static final void a(lg0 lg0Var, bd0 bd0Var, ng0 ng0Var) {
        w00 w00Var;
        for (bd0 bd0Var2 = bd0Var.i; bd0Var2 != null; bd0Var2 = bd0Var2.i) {
            if (bd0Var2 == lg0Var.b) {
                z40 s = lg0Var.a.s();
                if (s != null) {
                    w00Var = s.H.c;
                } else {
                    w00Var = null;
                }
                ng0Var.u = w00Var;
                lg0Var.d = ng0Var;
                return;
            }
            if ((bd0Var2.g & 2) == 0) {
                bd0Var2.C0(ng0Var);
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [r9, bd0] */
    public static bd0 b(ad0 ad0Var, bd0 bd0Var) {
        int i;
        bd0 bd0Var2;
        if (ad0Var instanceof gd0) {
            bd0Var2 = ((gd0) ad0Var).e();
            bd0Var2.g = og0.e(bd0Var2);
        } else {
            ?? bd0Var3 = new bd0();
            oe0 oe0Var = og0.a;
            if (ad0Var instanceof xz) {
                i = 5;
            } else {
                i = 1;
            }
            if (ad0Var instanceof ou0) {
                i |= 8;
            }
            if (ad0Var instanceof hb) {
                i |= 524288;
            }
            bd0Var3.g = i;
            bd0Var3.s = ad0Var;
            new HashSet();
            bd0Var2 = bd0Var3;
        }
        if (bd0Var2.r) {
            q00.b("A ModifierNodeElement cannot return an already attached node from create() ");
        }
        bd0Var2.m = true;
        bd0 bd0Var4 = bd0Var.j;
        if (bd0Var4 != null) {
            bd0Var4.i = bd0Var2;
            bd0Var2.j = bd0Var4;
        }
        bd0Var.j = bd0Var2;
        bd0Var2.i = bd0Var;
        return bd0Var2;
    }

    public static bd0 c(bd0 bd0Var) {
        boolean z = bd0Var.r;
        if (z) {
            oe0 oe0Var = og0.a;
            if (!z) {
                q00.b("autoInvalidateRemovedNode called on unattached node");
            }
            og0.a(bd0Var, -1, 2);
            bd0Var.A0();
            bd0Var.s0();
        }
        bd0 bd0Var2 = bd0Var.j;
        bd0 bd0Var3 = bd0Var.i;
        if (bd0Var2 != null) {
            bd0Var2.i = bd0Var3;
            bd0Var.j = null;
        }
        if (bd0Var3 != null) {
            bd0Var3.j = bd0Var2;
            bd0Var.i = null;
        }
        bd0Var3.getClass();
        return bd0Var3;
    }

    public static void h(ad0 ad0Var, ad0 ad0Var2, bd0 bd0Var) {
        int i;
        if ((ad0Var instanceof gd0) && (ad0Var2 instanceof gd0)) {
            bd0Var.getClass();
            ((gd0) ad0Var2).f(bd0Var);
            if (bd0Var.r) {
                og0.c(bd0Var);
                return;
            } else {
                bd0Var.n = true;
                return;
            }
        }
        if (bd0Var instanceof r9) {
            r9 r9Var = (r9) bd0Var;
            boolean z = r9Var.r;
            if (z) {
                if (!z) {
                    q00.b("unInitializeModifier called on unattached node");
                }
                if ((r9Var.g & 8) != 0) {
                    ((b4) k81.F(r9Var)).D();
                }
            }
            r9Var.s = ad0Var2;
            oe0 oe0Var = og0.a;
            if (ad0Var2 instanceof xz) {
                i = 5;
            } else {
                i = 1;
            }
            if (ad0Var2 instanceof ou0) {
                i |= 8;
            }
            if (ad0Var2 instanceof hb) {
                i |= 524288;
            }
            r9Var.g = i;
            if (r9Var.r) {
                r9Var.D0(false);
            }
            if (bd0Var.r) {
                og0.c(bd0Var);
                return;
            } else {
                bd0Var.n = true;
                return;
            }
        }
        q00.b("Unknown Modifier.Node type");
    }

    public final boolean d(int i) {
        if ((this.f.h & i) != 0) {
            return true;
        }
        return false;
    }

    public final void e() {
        for (bd0 bd0Var = this.f; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.z0();
            if (bd0Var.m) {
                oe0 oe0Var = og0.a;
                if (!bd0Var.r) {
                    q00.b("autoInvalidateInsertedNode called on unattached node");
                }
                og0.a(bd0Var, -1, 1);
            }
            if (bd0Var.n) {
                og0.c(bd0Var);
            }
            bd0Var.m = false;
            bd0Var.n = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x018f, code lost:
    
        r27 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0194, code lost:
    
        r25 = r22 + (r25 & r27);
        r22 = r11;
        r11 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x019e, code lost:
    
        if (r14 <= r7) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01a0, code lost:
    
        if (r11 <= r15) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01a2, code lost:
    
        r27 = r11;
        r28 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01ae, code lost:
    
        if (r0.a(r14 - 1, r27 - 1) == false) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01b0, code lost:
    
        r14 = r14 - 1;
        r11 = r27 - 1;
        r13 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01bb, code lost:
    
        r20[r17 + r28] = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01bf, code lost:
    
        if (r24 == 0) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01c1, code lost:
    
        r11 = r19 - r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x01c3, code lost:
    
        if (r11 < r12) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01c5, code lost:
    
        if (r11 > r3) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x01cb, code lost:
    
        if (r16[r17 + r11] < r14) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01cd, code lost:
    
        r26[r33] = r14;
        r11 = 1;
        r26[1] = r27;
        r26[r32] = r22;
        r26[3] = r25;
        r26[4] = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0262, code lost:
    
        r13 = r28 + 2;
        r11 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01b7, code lost:
    
        r27 = r11;
        r28 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0192, code lost:
    
        r27 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x018b, code lost:
    
        r25 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0179, code lost:
    
        r11 = r20[(r13 + 1) + r17];
        r14 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x016c, code lost:
    
        r24 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0177, code lost:
    
        r24 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0268, code lost:
    
        r3 = r3 + 1;
        r12 = r20;
        r11 = r21;
        r13 = r26;
        r14 = r29;
        r35 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0152, code lost:
    
        r11 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ce, code lost:
    
        if (r16[(r11 + 1) + r17] > r16[(r25 - 1) + r17]) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0148, code lost:
    
        r26 = r13;
        r29 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x014e, code lost:
    
        if ((r19 & 1) != 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0150, code lost:
    
        r11 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0154, code lost:
    
        r13 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0155, code lost:
    
        if (r13 > r3) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0157, code lost:
    
        if (r13 == r12) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0159, code lost:
    
        if (r13 == r3) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x015b, code lost:
    
        r24 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0169, code lost:
    
        if (r20[(r13 + 1) + r17] >= r20[(r13 - 1) + r17]) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x016e, code lost:
    
        r11 = r20[(r13 - 1) + r17];
        r14 = r11 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0180, code lost:
    
        r22 = r10 - ((r6 - r14) - r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0186, code lost:
    
        if (r3 == 0) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0188, code lost:
    
        r25 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x018d, code lost:
    
        if (r14 != r11) goto L76;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void f(int r32, defpackage.ef0 r33, defpackage.ef0 r34, defpackage.bd0 r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 921
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lg0.f(int, ef0, ef0, bd0, boolean):void");
    }

    public final void g() {
        z40 z40Var;
        w00 w00Var;
        t40 t40Var;
        bd0 bd0Var = this.e.i;
        ng0 ng0Var = this.c;
        bd0 bd0Var2 = bd0Var;
        while (true) {
            z40Var = this.a;
            if (bd0Var2 == null) {
                break;
            }
            r40 j = k81.j(bd0Var2);
            if (j != null) {
                ng0 ng0Var2 = bd0Var2.l;
                if (ng0Var2 != null) {
                    t40 t40Var2 = (t40) ng0Var2;
                    r40 r40Var = t40Var2.U;
                    t40Var2.p1(j);
                    t40Var = t40Var2;
                    if (r40Var != bd0Var2) {
                        lj0 lj0Var = t40Var2.P;
                        t40Var = t40Var2;
                        if (lj0Var != null) {
                            ((kx) lj0Var).c();
                            t40Var = t40Var2;
                        }
                    }
                } else {
                    t40 t40Var3 = new t40(z40Var, j);
                    bd0Var2.C0(t40Var3);
                    t40Var = t40Var3;
                }
                ng0Var.u = t40Var;
                t40Var.t = ng0Var;
                ng0Var = t40Var;
            } else {
                bd0Var2.C0(ng0Var);
            }
            bd0Var2 = bd0Var2.i;
        }
        z40 s = z40Var.s();
        if (s != null) {
            w00Var = s.H.c;
        } else {
            w00Var = null;
        }
        ng0Var.u = w00Var;
        this.d = ng0Var;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        bd0 bd0Var = this.f;
        e01 e01Var = this.e;
        if (bd0Var != e01Var) {
            while (true) {
                if (bd0Var == null || bd0Var == e01Var) {
                    break;
                }
                sb.append(String.valueOf(bd0Var));
                if (bd0Var.j == e01Var) {
                    sb.append("]");
                    break;
                }
                sb.append(",");
                bd0Var = bd0Var.j;
            }
        } else {
            sb.append("]");
        }
        return sb.toString();
    }
}
