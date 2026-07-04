package defpackage;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LocaleSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewParent;
import com.kyant.backdrop.catalog.R;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class y20 {
    public y20() {
        new ConcurrentHashMap();
    }

    public static final String A(int i) {
        if (i == 0) {
            return "android.widget.Button";
        }
        if (i == 1) {
            return "android.widget.CheckBox";
        }
        if (i == 3) {
            return "android.widget.RadioButton";
        }
        if (i == 5) {
            return "android.widget.ImageView";
        }
        if (i == 6) {
            return "android.widget.Spinner";
        }
        if (i == 7) {
            return "android.widget.NumberPicker";
        }
        return null;
    }

    public static final void B(su0 su0Var, int i, ft0 ft0Var) {
        su0 su0Var2;
        ef0 ef0Var = new ef0(new su0[16]);
        List i2 = su0Var.i(false, false);
        while (true) {
            ef0Var.d(ef0Var.g, i2);
            while (true) {
                int i3 = ef0Var.g;
                if (i3 != 0) {
                    su0Var2 = (su0) ef0Var.k(i3 - 1);
                    boolean u = o20.u(su0Var2);
                    nu0 nu0Var = su0Var2.d;
                    ve0 ve0Var = nu0Var.e;
                    if (!u && !ve0Var.c(wu0.j)) {
                        ng0 d = su0Var2.d();
                        if (d != null) {
                            z10 H = k81.H(o30.i(d, true));
                            if (H.a < H.c && H.b < H.d) {
                                Object g = nu0Var.e.g(mu0.e);
                                Object obj = null;
                                if (g == null) {
                                    g = null;
                                }
                                kv kvVar = (kv) g;
                                Object g2 = ve0Var.g(wu0.v);
                                if (g2 != null) {
                                    obj = g2;
                                }
                                et0 et0Var = (et0) obj;
                                if (kvVar != null && et0Var != null && ((Number) et0Var.b.a()).floatValue() > 0.0f) {
                                    int i4 = 1 + i;
                                    ft0Var.e(new gt0(su0Var2, i4, H, d));
                                    B(su0Var2, i4, ft0Var);
                                }
                            }
                        } else {
                            throw d3.t("Expected semantics node to have a coordinator.");
                        }
                    }
                } else {
                    return;
                }
            }
            i2 = su0Var2.i(false, false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /* JADX WARN: Type inference failed for: r9v3, types: [ep0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object C(long r6, defpackage.kv r8, defpackage.jj r9) {
        /*
            boolean r0 = r9 instanceof defpackage.g21
            if (r0 == 0) goto L13
            r0 = r9
            g21 r0 = (defpackage.g21) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            g21 r0 = new g21
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.i
            int r1 = r0.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L30
            if (r1 != r3) goto L2a
            ep0 r6 = r0.h
            defpackage.o30.x(r9)     // Catch: defpackage.e21 -> L28
            return r9
        L28:
            r7 = move-exception
            goto L56
        L2a:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r6)
            return r2
        L30:
            defpackage.o30.x(r9)
            r4 = 0
            int r9 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r9 > 0) goto L3a
            goto L5c
        L3a:
            ep0 r9 = new ep0
            r9.<init>()
            r0.h = r9     // Catch: defpackage.e21 -> L54
            r0.j = r3     // Catch: defpackage.e21 -> L54
            f21 r1 = new f21     // Catch: defpackage.e21 -> L54
            r1.<init>(r6, r0)     // Catch: defpackage.e21 -> L54
            r9.e = r1     // Catch: defpackage.e21 -> L54
            java.lang.Object r6 = z(r1, r8)     // Catch: defpackage.e21 -> L54
            ik r7 = defpackage.ik.e
            if (r6 != r7) goto L53
            return r7
        L53:
            return r6
        L54:
            r7 = move-exception
            r6 = r9
        L56:
            f21 r8 = r7.e
            java.lang.Object r6 = r6.e
            if (r8 != r6) goto L5d
        L5c:
            return r2
        L5d:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.y20.C(long, kv, jj):java.lang.Object");
    }

    public static final long a(int i) {
        long j = i << 32;
        int i2 = v30.p;
        return j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x028e, code lost:
    
        if (r47.g(true) != false) goto L173;
     */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0306 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x02c8  */
    /* JADX WARN: Type inference failed for: r2v21, types: [java.lang.Object, t50] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void b(final defpackage.cd0 r38, defpackage.m70 r39, final defpackage.tj0 r40, final defpackage.rl r41, final boolean r42, final defpackage.e5 r43, final defpackage.z9 r44, final defpackage.y7 r45, final defpackage.gv r46, defpackage.bw r47, final int r48, final int r49) {
        /*
            Method dump skipped, instructions count: 973
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.y20.b(cd0, m70, tj0, rl, boolean, e5, z9, y7, gv, bw, int, int):void");
    }

    public static final void c(vu vuVar, gv gvVar, m9 m9Var, int i, cd0 cd0Var, gg ggVar, bw bwVar, int i2) {
        int i3;
        int i4;
        int i5;
        boolean z;
        cd0 cd0Var2;
        long f;
        long b;
        vuVar.getClass();
        gvVar.getClass();
        m9Var.getClass();
        bwVar.W(1301077120);
        if (bwVar.h(vuVar)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i6 = i2 | i3;
        if (bwVar.h(gvVar)) {
            i4 = 32;
        } else {
            i4 = 16;
        }
        int i7 = i6 | i4;
        if (bwVar.h(m9Var)) {
            i5 = 256;
        } else {
            i5 = 128;
        }
        int i8 = i7 | i5;
        if ((74899 & i8) != 74898) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i8 & 1, z)) {
            boolean D = n20.D(bwVar);
            boolean z2 = !D;
            if (!D) {
                f = f31.f(4278225151L);
            } else {
                f = f31.f(4278227455L);
            }
            long j = f;
            if (!D) {
                b = se.b(f31.f(4294638330L), 0.4f);
            } else {
                b = se.b(f31.f(4279374354L), 0.4f);
            }
            cd0Var2 = cd0Var;
            n20.a(cd0Var2, x1.j, jc0.C(-1301534742, new s90(vuVar, gvVar, m9Var, b, ggVar, jc0.E(bwVar), z2, i, j), bwVar), bwVar, 3126);
        } else {
            cd0Var2 = cd0Var;
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new t90(vuVar, gvVar, m9Var, i, cd0Var2, ggVar, i2);
        }
    }

    public static final int d(float f) {
        return Math.round((float) Math.ceil(f));
    }

    public static final boolean g(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return false;
    }

    public static qu h(qu[] quVarArr) {
        qu quVar = null;
        int i = Integer.MAX_VALUE;
        for (qu quVar2 : quVarArr) {
            int abs = (Math.abs(quVar2.c - 400) * 2) + (quVar2.d ? 1 : 0);
            if (quVar == null || i > abs) {
                quVar = quVar2;
                i = abs;
            }
        }
        return quVar;
    }

    public static final String i(Object obj) {
        return obj + " cannot be saved using the current SaveableStateRegistry. The default implementation only supports types which can be stored inside the Bundle. Please consider implementing a custom Saver for this class and pass it to rememberSaveable().";
    }

    public static final ViewParent j(View view) {
        view.getClass();
        ViewParent parent = view.getParent();
        if (parent != null) {
            return parent;
        }
        Object tag = view.getTag(R.id.view_tree_disjoint_parent);
        if (tag instanceof ViewParent) {
            return (ViewParent) tag;
        }
        return null;
    }

    public static final qb0 k(qb0 qb0Var) {
        z40 z40Var;
        z40 z40Var2 = qb0Var.s.s;
        while (true) {
            z40 s = z40Var2.s();
            z40 z40Var3 = null;
            if (s != null) {
                z40Var = s.l;
            } else {
                z40Var = null;
            }
            if (z40Var != null) {
                z40 s2 = z40Var2.s();
                if (s2 != null) {
                    z40Var3 = s2.l;
                }
                z40Var3.getClass();
                z40 s3 = z40Var2.s();
                s3.getClass();
                z40Var2 = s3.l;
                z40Var2.getClass();
            } else {
                qb0 N0 = z40Var2.H.d.N0();
                N0.getClass();
                return N0;
            }
        }
    }

    public static final h11 l(nu0 nu0Var) {
        gv gvVar;
        ArrayList arrayList = new ArrayList();
        Object g = nu0Var.e.g(mu0.a);
        if (g == null) {
            g = null;
        }
        n0 n0Var = (n0) g;
        if (n0Var == null || (gvVar = (gv) n0Var.b) == null || !((Boolean) gvVar.e(arrayList)).booleanValue()) {
            return null;
        }
        return (h11) arrayList.get(0);
    }

    public static final boolean m(float[] fArr, float[] fArr2) {
        boolean z;
        if (fArr.length < 16 || fArr2.length < 16) {
            return false;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        float f4 = fArr[3];
        float f5 = fArr[4];
        float f6 = fArr[5];
        float f7 = fArr[6];
        float f8 = fArr[7];
        float f9 = fArr[8];
        float f10 = fArr[9];
        float f11 = fArr[10];
        float f12 = fArr[11];
        float f13 = fArr[12];
        float f14 = fArr[13];
        float f15 = fArr[14];
        float f16 = fArr[15];
        float f17 = (f * f6) - (f2 * f5);
        float f18 = (f * f7) - (f3 * f5);
        float f19 = (f * f8) - (f4 * f5);
        float f20 = (f2 * f7) - (f3 * f6);
        float f21 = (f2 * f8) - (f4 * f6);
        float f22 = (f3 * f8) - (f4 * f7);
        float f23 = (f9 * f14) - (f10 * f13);
        float f24 = (f9 * f15) - (f11 * f13);
        float f25 = (f9 * f16) - (f12 * f13);
        float f26 = (f10 * f15) - (f11 * f14);
        float f27 = (f10 * f16) - (f12 * f14);
        float f28 = (f11 * f16) - (f12 * f15);
        float f29 = (f22 * f23) + (((f20 * f25) + ((f19 * f26) + ((f17 * f28) - (f18 * f27)))) - (f21 * f24));
        if (f29 != 0.0f) {
            float f30 = 1.0f / f29;
            fArr2[0] = ((f8 * f26) + ((f6 * f28) - (f7 * f27))) * f30;
            fArr2[1] = (((f3 * f27) + ((-f2) * f28)) - (f4 * f26)) * f30;
            fArr2[2] = ((f16 * f20) + ((f14 * f22) - (f15 * f21))) * f30;
            fArr2[3] = (((f11 * f21) + ((-f10) * f22)) - (f12 * f20)) * f30;
            float f31 = -f5;
            fArr2[4] = (((f7 * f25) + (f31 * f28)) - (f8 * f24)) * f30;
            fArr2[5] = ((f4 * f24) + ((f28 * f) - (f3 * f25))) * f30;
            float f32 = -f13;
            fArr2[6] = (((f15 * f19) + (f32 * f22)) - (f16 * f18)) * f30;
            fArr2[7] = ((f12 * f18) + ((f22 * f9) - (f11 * f19))) * f30;
            fArr2[8] = ((f8 * f23) + ((f5 * f27) - (f6 * f25))) * f30;
            fArr2[9] = (((f25 * f2) + ((-f) * f27)) - (f4 * f23)) * f30;
            fArr2[10] = ((f16 * f17) + ((f13 * f21) - (f14 * f19))) * f30;
            fArr2[11] = (((f19 * f10) + ((-f9) * f21)) - (f12 * f17)) * f30;
            fArr2[12] = (((f6 * f24) + (f31 * f26)) - (f7 * f23)) * f30;
            fArr2[13] = ((f3 * f23) + ((f * f26) - (f2 * f24))) * f30;
            fArr2[14] = (((f14 * f18) + (f32 * f20)) - (f15 * f17)) * f30;
            fArr2[15] = ((f11 * f17) + ((f9 * f20) - (f10 * f18))) * f30;
        }
        if (f29 == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        return !z;
    }

    public static final boolean n() {
        if (Build.VERSION.SDK_INT >= 33) {
            return true;
        }
        return false;
    }

    public static qc0 o(mr0 mr0Var, int i, int i2, int i3, int i4, int i5, rc0 rc0Var, List list, em0[] em0VarArr, int i6) {
        int i7;
        int i8;
        float f;
        nr0 nr0Var;
        float f2;
        boolean z;
        int i9;
        nr0 nr0Var2;
        float f3;
        float f4;
        int i10;
        int i11;
        List list2 = list;
        long j = i5;
        int[] iArr = new int[i6];
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        float f5 = 0.0f;
        while (true) {
            nr0 nr0Var3 = null;
            if (i13 >= i6) {
                break;
            }
            kc0 kc0Var = (kc0) list2.get(i13);
            long j2 = j;
            Object A = kc0Var.A();
            if (A instanceof nr0) {
                nr0Var3 = (nr0) A;
            }
            nr0 nr0Var4 = nr0Var3;
            if (nr0Var4 != null) {
                f4 = nr0Var4.a;
            } else {
                f4 = 0.0f;
            }
            if (f4 > 0.0f) {
                f5 += f4;
                i14++;
            } else {
                int i17 = i3 - i15;
                em0 em0Var = em0VarArr[i13];
                if (em0Var == null) {
                    if (i3 == Integer.MAX_VALUE) {
                        i10 = i17;
                        i11 = Integer.MAX_VALUE;
                    } else if (i17 < 0) {
                        i10 = i17;
                        i11 = 0;
                    } else {
                        i11 = i17;
                        i10 = i11;
                    }
                    em0Var = kc0Var.v(mr0Var.c(0, i11, i4, false));
                } else {
                    i10 = i17;
                }
                int f6 = mr0Var.f(em0Var);
                int d = mr0Var.d(em0Var);
                iArr[i13] = f6;
                int i18 = i10 - f6;
                if (i18 < 0) {
                    i18 = 0;
                }
                i16 = Math.min(i5, i18);
                i15 += f6 + i16;
                i12 = Math.max(i12, d);
                em0VarArr[i13] = em0Var;
            }
            i13++;
            j = j2;
        }
        long j3 = j;
        if (i14 == 0) {
            i15 -= i16;
            i8 = 0;
        } else {
            if (i3 != Integer.MAX_VALUE) {
                i7 = i3;
            } else {
                i7 = i;
            }
            long j4 = (i14 - 1) * j3;
            long j5 = (i7 - i15) - j4;
            if (j5 < 0) {
                j5 = 0;
            }
            float f7 = ((float) j5) / f5;
            for (int i19 = 0; i19 < i6; i19++) {
                Object A2 = ((kc0) list2.get(i19)).A();
                if (A2 instanceof nr0) {
                    nr0Var2 = (nr0) A2;
                } else {
                    nr0Var2 = null;
                }
                if (nr0Var2 != null) {
                    f3 = nr0Var2.a;
                } else {
                    f3 = 0.0f;
                }
                j5 -= Math.round(f3 * f7);
            }
            int i20 = 0;
            int i21 = 0;
            while (i21 < i6) {
                if (em0VarArr[i21] == null) {
                    kc0 kc0Var2 = (kc0) list2.get(i21);
                    Object A3 = kc0Var2.A();
                    f = f7;
                    if (A3 instanceof nr0) {
                        nr0Var = (nr0) A3;
                    } else {
                        nr0Var = null;
                    }
                    if (nr0Var != null) {
                        f2 = nr0Var.a;
                    } else {
                        f2 = 0.0f;
                    }
                    if (f2 <= 0.0f) {
                        o00.b("All weights <= 0 should have placeables");
                    }
                    float f8 = f2;
                    int signum = Long.signum(j5);
                    j5 -= signum;
                    int max = Math.max(0, Math.round(f8 * f) + signum);
                    if (nr0Var != null) {
                        z = nr0Var.b;
                    } else {
                        z = true;
                    }
                    if (z && max != Integer.MAX_VALUE) {
                        i9 = max;
                        em0 v = kc0Var2.v(mr0Var.c(i9, max, i4, true));
                        int f9 = mr0Var.f(v);
                        int d2 = mr0Var.d(v);
                        iArr[i21] = f9;
                        i20 += f9;
                        int max2 = Math.max(i12, d2);
                        em0VarArr[i21] = v;
                        i12 = max2;
                    }
                    i9 = 0;
                    em0 v2 = kc0Var2.v(mr0Var.c(i9, max, i4, true));
                    int f92 = mr0Var.f(v2);
                    int d22 = mr0Var.d(v2);
                    iArr[i21] = f92;
                    i20 += f92;
                    int max22 = Math.max(i12, d22);
                    em0VarArr[i21] = v2;
                    i12 = max22;
                } else {
                    f = f7;
                }
                i21++;
                list2 = list;
                f7 = f;
            }
            i8 = (int) (i20 + j4);
            int i22 = i3 - i15;
            if (i8 < 0) {
                i8 = 0;
            }
            if (i8 > i22) {
                i8 = i22;
            }
        }
        int i23 = i8 + i15;
        if (i23 < 0) {
            i23 = 0;
        }
        int max3 = Math.max(i23, i);
        int max4 = Math.max(i12, Math.max(i2, 0));
        int[] iArr2 = new int[i6];
        mr0Var.b(max3, rc0Var, iArr, iArr2);
        return mr0Var.a(em0VarArr, rc0Var, iArr2, max3, max4);
    }

    public static final ux0 p(ux0 ux0Var, ux0 ux0Var2) {
        if (ux0Var == null) {
            return ux0Var2;
        }
        if (ux0Var2 == null) {
            return ux0Var;
        }
        a11 a11Var = ux0Var2.a;
        return vx0.a(ux0Var, a11Var.a(), a11Var.d(), a11Var.r(), ux0Var2.b, ux0Var2.c, ux0Var2.d, ux0Var2.e, ux0Var2.f, ux0Var2.g, ux0Var2.h, ux0Var2.i, ux0Var2.j, ux0Var2.k, ux0Var2.l, ux0Var2.m, ux0Var2.n, ux0Var2.o);
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [dc0, uc0] */
    public static uc0 q(MappedByteBuffer mappedByteBuffer) {
        long j;
        ByteBuffer duplicate = mappedByteBuffer.duplicate();
        duplicate.order(ByteOrder.BIG_ENDIAN);
        duplicate.position(duplicate.position() + 4);
        int i = duplicate.getShort() & 65535;
        if (i <= 100) {
            duplicate.position(duplicate.position() + 6);
            int i2 = 0;
            while (true) {
                if (i2 < i) {
                    int i3 = duplicate.getInt();
                    duplicate.position(duplicate.position() + 4);
                    j = duplicate.getInt() & 4294967295L;
                    duplicate.position(duplicate.position() + 4);
                    if (1835365473 == i3) {
                        break;
                    }
                    i2++;
                } else {
                    j = -1;
                    break;
                }
            }
            if (j != -1) {
                duplicate.position(duplicate.position() + ((int) (j - duplicate.position())));
                duplicate.position(duplicate.position() + 12);
                long j2 = duplicate.getInt() & 4294967295L;
                for (int i4 = 0; i4 < j2; i4++) {
                    int i5 = duplicate.getInt();
                    long j3 = duplicate.getInt() & 4294967295L;
                    duplicate.getInt();
                    if (1164798569 == i5 || 1701669481 == i5) {
                        duplicate.position((int) (j3 + j));
                        ?? dc0Var = new dc0();
                        duplicate.order(ByteOrder.LITTLE_ENDIAN);
                        int position = duplicate.position() + duplicate.getInt(duplicate.position());
                        dc0Var.h = duplicate;
                        dc0Var.e = position;
                        int i6 = position - duplicate.getInt(position);
                        dc0Var.f = i6;
                        dc0Var.g = ((ByteBuffer) dc0Var.h).getShort(i6);
                        return dc0Var;
                    }
                }
            }
            throw new IOException("Cannot read metadata.");
        }
        throw new IOException("Cannot read metadata.");
    }

    public static final Object r(Object[] objArr, vu vuVar, bw bwVar) {
        return s(Arrays.copyOf(objArr, objArr.length), f31.i, vuVar, bwVar, 3456);
    }

    public static final Object s(Object[] objArr, ss0 ss0Var, vu vuVar, bw bwVar, int i) {
        Object[] objArr2;
        ss0 ss0Var2;
        boolean z;
        final Object obj;
        Object obj2;
        Object e;
        long j = bwVar.T;
        k81.m(36);
        final String l = Long.toString(j, 36);
        l.getClass();
        ss0Var.getClass();
        final es0 es0Var = (es0) bwVar.j(gs0.a);
        Object L = bwVar.L();
        Object obj3 = null;
        Object obj4 = ph.a;
        if (L == obj4) {
            if (es0Var != null && (e = es0Var.e(l)) != null) {
                obj2 = ss0Var.g(e);
            } else {
                obj2 = null;
            }
            if (obj2 == null) {
                obj2 = vuVar.a();
            }
            objArr2 = objArr;
            ss0Var2 = ss0Var;
            Object bs0Var = new bs0(ss0Var2, es0Var, l, obj2, objArr2);
            bwVar.f0(bs0Var);
            L = bs0Var;
        } else {
            objArr2 = objArr;
            ss0Var2 = ss0Var;
        }
        final bs0 bs0Var2 = (bs0) L;
        if (Arrays.equals(objArr2, bs0Var2.i)) {
            obj3 = bs0Var2.h;
        }
        if (obj3 == null) {
            obj3 = vuVar.a();
        }
        boolean h = bwVar.h(bs0Var2);
        if ((((i & 112) ^ 48) > 32 && bwVar.h(ss0Var2)) || (i & 48) == 32) {
            z = true;
        } else {
            z = false;
        }
        boolean h2 = h | z | bwVar.h(es0Var) | bwVar.f(l) | bwVar.h(obj3) | bwVar.h(objArr2);
        Object L2 = bwVar.L();
        if (!h2 && L2 != obj4) {
            obj = obj3;
        } else {
            final Object[] objArr3 = objArr2;
            obj = obj3;
            final ss0 ss0Var3 = ss0Var2;
            Object obj5 = new vu() { // from class: op0
                @Override // defpackage.vu
                public final Object a() {
                    boolean z2;
                    bs0 bs0Var3 = bs0.this;
                    es0 es0Var2 = bs0Var3.f;
                    es0 es0Var3 = es0Var;
                    boolean z3 = true;
                    if (es0Var2 != es0Var3) {
                        bs0Var3.f = es0Var3;
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    String str = bs0Var3.g;
                    String str2 = l;
                    if (!o20.e(str, str2)) {
                        bs0Var3.g = str2;
                    } else {
                        z3 = z2;
                    }
                    bs0Var3.e = ss0Var3;
                    bs0Var3.h = obj;
                    bs0Var3.i = objArr3;
                    r7 r7Var = bs0Var3.j;
                    if (r7Var != null && z3) {
                        r7Var.H();
                        bs0Var3.j = null;
                        bs0Var3.a();
                    }
                    return x31.a;
                }
            };
            bwVar.f0(obj5);
            L2 = obj5;
        }
        dl.j((vu) L2, bwVar);
        return obj;
    }

    public static final Object t(Object[] objArr, ss0 ss0Var, vu vuVar, bw bwVar, int i) {
        return s(Arrays.copyOf(objArr, objArr.length), ss0Var, vuVar, bwVar, ((i << 3) & 7168) | 384);
    }

    public static final float u(long j, float f, mm mmVar) {
        float c;
        long b = t11.b(j);
        if (u11.a(b, 4294967296L)) {
            if (mmVar.y() > 1.05d) {
                c = t11.c(j) / t11.c(mmVar.j0(f));
            } else {
                return mmVar.d0(j);
            }
        } else if (u11.a(b, 8589934592L)) {
            c = t11.c(j);
        } else {
            return Float.NaN;
        }
        return c * f;
    }

    public static final void v(t6 t6Var, int i) {
        Object obj;
        Iterator<T> it = t6Var.getLayoutNodeToHolder().entrySet().iterator();
        while (true) {
            if (it.hasNext()) {
                obj = it.next();
                if (((z40) ((Map.Entry) obj).getKey()).f == i) {
                    break;
                }
            } else {
                obj = null;
                break;
            }
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry != null && entry.getValue() != null) {
            v7.d();
        }
    }

    public static final void w(Spannable spannable, long j, int i, int i2) {
        if (j != 16) {
            spannable.setSpan(new ForegroundColorSpan(f31.P(j)), i, i2, 33);
        }
    }

    public static final void x(Spannable spannable, long j, mm mmVar, int i, int i2) {
        long b = t11.b(j);
        if (u11.a(b, 4294967296L)) {
            spannable.setSpan(new AbsoluteSizeSpan(jc0.G(mmVar.d0(j)), false), i, i2, 33);
        } else if (u11.a(b, 8589934592L)) {
            spannable.setSpan(new RelativeSizeSpan(t11.c(j)), i, i2, 33);
        }
    }

    public static final void y(Spannable spannable, ua0 ua0Var, int i, int i2) {
        ta0 a;
        LocaleSpan localeSpan;
        if (ua0Var != null) {
            List list = ua0Var.e;
            if (Build.VERSION.SDK_INT >= 24) {
                ArrayList arrayList = new ArrayList(ne.N(ua0Var));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ta0) it.next()).a);
                }
                Locale[] localeArr = (Locale[]) arrayList.toArray(new Locale[0]);
                localeSpan = x0.f(x0.e((Locale[]) Arrays.copyOf(localeArr, localeArr.length)));
            } else {
                if (list.isEmpty()) {
                    a = km0.a.g().a();
                } else {
                    a = ua0Var.a();
                }
                localeSpan = new LocaleSpan(a.a);
            }
            spannable.setSpan(localeSpan, i, i2, 33);
        }
    }

    public static final Object z(f21 f21Var, kv kvVar) {
        Object qfVar;
        Object W;
        g30.v(f21Var, true, new xn(f31.A(f21Var.j.r()).d(f21Var.k, f21Var, f21Var.i)));
        try {
            if (!(kvVar instanceof s9)) {
                qfVar = t20.U(kvVar, f21Var, f21Var);
            } else {
                f31.n(2, kvVar);
                qfVar = kvVar.d(f21Var, f21Var);
            }
        } catch (Throwable th) {
            qfVar = new qf(th, false);
        }
        ik ikVar = ik.e;
        if (qfVar != ikVar && (W = f21Var.W(qfVar)) != o20.h) {
            if (W instanceof qf) {
                Throwable th2 = ((qf) W).a;
                if (th2 instanceof e21) {
                    if (((e21) th2).e == f21Var) {
                        if (qfVar instanceof qf) {
                            throw ((qf) qfVar).a;
                        }
                    } else {
                        throw th2;
                    }
                } else {
                    throw th2;
                }
            } else {
                qfVar = o20.K(W);
            }
            return qfVar;
        }
        return ikVar;
    }

    public abstract void e();

    public abstract Typeface f(Context context, qu[] quVarArr);
}
