package defpackage;

import android.graphics.Rect;
import android.graphics.RenderEffect;
import android.os.Build;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class d20 {
    public static final long A(long j, float f) {
        long floatToRawIntBits = j | (Float.floatToRawIntBits(f) & 4294967295L);
        u11[] u11VarArr = t11.b;
        return floatToRawIntBits;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.lang.Object[], java.lang.Object] */
    public static final boolean B(pt ptVar, oj ojVar) {
        pt[] ptVarArr = new pt[16];
        if (!ptVar.e.r) {
            q00.b("visitChildren called on an unattached node");
        }
        ef0 ef0Var = new ef0(new bd0[16]);
        bd0 bd0Var = ptVar.e;
        bd0 bd0Var2 = bd0Var.j;
        if (bd0Var2 == null) {
            k81.f(ef0Var, bd0Var);
        } else {
            ef0Var.b(bd0Var2);
        }
        int i = 0;
        while (true) {
            int i2 = ef0Var.g;
            if (i2 == 0) {
                break;
            }
            bd0 bd0Var3 = (bd0) ef0Var.k(i2 - 1);
            if ((bd0Var3.h & 1024) == 0) {
                k81.f(ef0Var, bd0Var3);
            } else {
                while (true) {
                    if (bd0Var3 == null) {
                        break;
                    }
                    if ((bd0Var3.g & 1024) != 0) {
                        ef0 ef0Var2 = null;
                        while (bd0Var3 != null) {
                            if (bd0Var3 instanceof pt) {
                                pt ptVar2 = (pt) bd0Var3;
                                int i3 = i + 1;
                                if (ptVarArr.length < i3) {
                                    int length = ptVarArr.length;
                                    ?? r10 = new Object[Math.max(i3, length * 2)];
                                    System.arraycopy(ptVarArr, 0, r10, 0, length);
                                    ptVarArr = r10;
                                }
                                ptVarArr[i] = ptVar2;
                                i = i3;
                            } else if ((bd0Var3.g & 1024) != 0 && (bd0Var3 instanceof jm)) {
                                int i4 = 0;
                                for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                    if ((bd0Var4.g & 1024) != 0) {
                                        i4++;
                                        if (i4 == 1) {
                                            bd0Var3 = bd0Var4;
                                        } else {
                                            if (ef0Var2 == null) {
                                                ef0Var2 = new ef0(new bd0[16]);
                                            }
                                            if (bd0Var3 != null) {
                                                ef0Var2.b(bd0Var3);
                                                bd0Var3 = null;
                                            }
                                            ef0Var2.b(bd0Var4);
                                        }
                                    }
                                }
                                if (i4 == 1) {
                                }
                            }
                            bd0Var3 = k81.h(ef0Var2);
                        }
                    } else {
                        bd0Var3 = bd0Var3.j;
                    }
                }
            }
        }
        Arrays.sort(ptVarArr, 0, i, qt.b);
        int i5 = i - 1;
        if (i5 < ptVarArr.length) {
            while (i5 >= 0) {
                pt ptVar3 = ptVarArr[i5];
                if (n20.B(ptVar3) && i(ptVar3, ojVar)) {
                    return true;
                }
                i5--;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.lang.Object[], java.lang.Object] */
    public static final boolean C(pt ptVar, oj ojVar) {
        pt[] ptVarArr = new pt[16];
        if (!ptVar.e.r) {
            q00.b("visitChildren called on an unattached node");
        }
        ef0 ef0Var = new ef0(new bd0[16]);
        bd0 bd0Var = ptVar.e;
        bd0 bd0Var2 = bd0Var.j;
        if (bd0Var2 == null) {
            k81.f(ef0Var, bd0Var);
        } else {
            ef0Var.b(bd0Var2);
        }
        int i = 0;
        while (true) {
            int i2 = ef0Var.g;
            if (i2 == 0) {
                break;
            }
            bd0 bd0Var3 = (bd0) ef0Var.k(i2 - 1);
            if ((bd0Var3.h & 1024) == 0) {
                k81.f(ef0Var, bd0Var3);
            } else {
                while (true) {
                    if (bd0Var3 == null) {
                        break;
                    }
                    if ((bd0Var3.g & 1024) != 0) {
                        ef0 ef0Var2 = null;
                        while (bd0Var3 != null) {
                            if (bd0Var3 instanceof pt) {
                                pt ptVar2 = (pt) bd0Var3;
                                int i3 = i + 1;
                                if (ptVarArr.length < i3) {
                                    int length = ptVarArr.length;
                                    ?? r10 = new Object[Math.max(i3, length * 2)];
                                    System.arraycopy(ptVarArr, 0, r10, 0, length);
                                    ptVarArr = r10;
                                }
                                ptVarArr[i] = ptVar2;
                                i = i3;
                            } else if ((bd0Var3.g & 1024) != 0 && (bd0Var3 instanceof jm)) {
                                int i4 = 0;
                                for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                    if ((bd0Var4.g & 1024) != 0) {
                                        i4++;
                                        if (i4 == 1) {
                                            bd0Var3 = bd0Var4;
                                        } else {
                                            if (ef0Var2 == null) {
                                                ef0Var2 = new ef0(new bd0[16]);
                                            }
                                            if (bd0Var3 != null) {
                                                ef0Var2.b(bd0Var3);
                                                bd0Var3 = null;
                                            }
                                            ef0Var2.b(bd0Var4);
                                        }
                                    }
                                }
                                if (i4 == 1) {
                                }
                            }
                            bd0Var3 = k81.h(ef0Var2);
                        }
                    } else {
                        bd0Var3 = bd0Var3.j;
                    }
                }
            }
        }
        Arrays.sort(ptVarArr, 0, i, qt.b);
        for (int i5 = 0; i5 < i; i5++) {
            pt ptVar3 = ptVarArr[i5];
            if (n20.B(ptVar3) && q(ptVar3, ojVar)) {
                return true;
            }
        }
        return false;
    }

    public static final void D(float[] fArr, float[] fArr2, int i, float[] fArr3) {
        float m;
        if (i == 0) {
            q00.a("At least one point must be provided");
        }
        int i2 = 2 >= i ? i - 1 : 2;
        int i3 = i2 + 1;
        float[][] fArr4 = new float[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            fArr4[i4] = new float[i];
        }
        for (int i5 = 0; i5 < i; i5++) {
            fArr4[0][i5] = 1.0f;
            for (int i6 = 1; i6 < i3; i6++) {
                fArr4[i6][i5] = fArr4[i6 - 1][i5] * fArr[i5];
            }
        }
        float[][] fArr5 = new float[i3];
        for (int i7 = 0; i7 < i3; i7++) {
            fArr5[i7] = new float[i];
        }
        float[][] fArr6 = new float[i3];
        for (int i8 = 0; i8 < i3; i8++) {
            fArr6[i8] = new float[i3];
        }
        for (int i9 = 0; i9 < i3; i9++) {
            float[] fArr7 = fArr5[i9];
            float[] fArr8 = fArr4[i9];
            fArr8.getClass();
            fArr7.getClass();
            System.arraycopy(fArr8, 0, fArr7, 0, i);
            for (int i10 = 0; i10 < i9; i10++) {
                float[] fArr9 = fArr5[i10];
                float m2 = m(fArr7, fArr9);
                for (int i11 = 0; i11 < i; i11++) {
                    fArr7[i11] = fArr7[i11] - (fArr9[i11] * m2);
                }
            }
            float sqrt = (float) Math.sqrt(m(fArr7, fArr7));
            if (sqrt < 1.0E-6f) {
                sqrt = 1.0E-6f;
            }
            float f = 1.0f / sqrt;
            for (int i12 = 0; i12 < i; i12++) {
                fArr7[i12] = fArr7[i12] * f;
            }
            float[] fArr10 = fArr6[i9];
            for (int i13 = 0; i13 < i3; i13++) {
                if (i13 < i9) {
                    m = 0.0f;
                } else {
                    m = m(fArr7, fArr4[i13]);
                }
                fArr10[i13] = m;
            }
        }
        for (int i14 = i2; -1 < i14; i14--) {
            float m3 = m(fArr5[i14], fArr2);
            float[] fArr11 = fArr6[i14];
            int i15 = i14 + 1;
            if (i15 <= i2) {
                int i16 = i2;
                while (true) {
                    m3 -= fArr11[i16] * fArr3[i16];
                    if (i16 != i15) {
                        i16--;
                    }
                }
            }
            fArr3[i14] = m3 / fArr11[i14];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x02b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void E(android.view.ViewStructure r38, defpackage.z40 r39, android.view.autofill.AutofillId r40, java.lang.String r41, defpackage.yo0 r42) {
        /*
            Method dump skipped, instructions count: 1057
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d20.E(android.view.ViewStructure, z40, android.view.autofill.AutofillId, java.lang.String, yo0):void");
    }

    public static final void F(Object[] objArr, int i, int i2) {
        objArr.getClass();
        while (i < i2) {
            objArr[i] = null;
            i++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:133:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0197 A[EDGE_INSN: B:151:0x0197->B:132:0x0197 BREAK  A[LOOP:5: B:91:0x012c->B:146:0x012c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x012e  */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.lang.Object[], java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean G(defpackage.pt r12, defpackage.pt r13, int r14, defpackage.oj r15) {
        /*
            Method dump skipped, instructions count: 434
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d20.G(pt, pt, int, oj):boolean");
    }

    public static final long H(long j, long j2) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) * Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) * Float.intBitsToFloat((int) (j & 4294967295L));
        return (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
    }

    public static final long I(long j) {
        return (((int) Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (((int) Float.intBitsToFloat((int) (j >> 32))) << 32);
    }

    public static final long J(long j) {
        return (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j >> 32)) << 32);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [gv] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    public static final void K(bd0 bd0Var, Object obj, gv gvVar) {
        lg0 lg0Var;
        if (!bd0Var.e.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var2 = bd0Var.e.i;
        z40 E = k81.E(bd0Var);
        while (E != null) {
            if ((E.H.f.h & 262144) != 0) {
                while (bd0Var2 != null) {
                    if ((bd0Var2.g & 262144) != 0) {
                        jm jmVar = bd0Var2;
                        ?? r4 = 0;
                        while (jmVar != 0) {
                            boolean z = true;
                            if (jmVar instanceof w21) {
                                w21 w21Var = (w21) jmVar;
                                if (obj.equals(w21Var.z())) {
                                    z = ((Boolean) gvVar.e(w21Var)).booleanValue();
                                }
                                if (!z) {
                                    return;
                                }
                            } else if ((jmVar.g & 262144) != 0 && (jmVar instanceof jm)) {
                                bd0 bd0Var3 = jmVar.t;
                                int i = 0;
                                jmVar = jmVar;
                                r4 = r4;
                                while (bd0Var3 != null) {
                                    if ((bd0Var3.g & 262144) != 0) {
                                        i++;
                                        r4 = r4;
                                        if (i == 1) {
                                            jmVar = bd0Var3;
                                        } else {
                                            if (r4 == 0) {
                                                r4 = new ef0(new bd0[16]);
                                            }
                                            if (jmVar != 0) {
                                                r4.b(jmVar);
                                                jmVar = 0;
                                            }
                                            r4.b(bd0Var3);
                                        }
                                    }
                                    bd0Var3 = bd0Var3.j;
                                    jmVar = jmVar;
                                    r4 = r4;
                                }
                                if (i == 1) {
                                }
                            }
                            jmVar = k81.h(r4);
                        }
                    }
                    bd0Var2 = bd0Var2.i;
                }
            }
            E = E.s();
            if (E != null && (lg0Var = E.H) != null) {
                bd0Var2 = lg0Var.e;
            } else {
                bd0Var2 = null;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0, types: [gv] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public static final void L(bd0 bd0Var, String str, gv gvVar) {
        v21 v21Var;
        if (!bd0Var.e.r) {
            q00.b("visitSubtreeIf called on an unattached node");
        }
        ef0 ef0Var = new ef0(new bd0[16]);
        bd0 bd0Var2 = bd0Var.e;
        bd0 bd0Var3 = bd0Var2.j;
        if (bd0Var3 == null) {
            k81.f(ef0Var, bd0Var2);
        } else {
            ef0Var.b(bd0Var3);
        }
        while (true) {
            int i = ef0Var.g;
            if (i != 0) {
                bd0 bd0Var4 = (bd0) ef0Var.k(i - 1);
                if ((bd0Var4.h & 262144) != 0) {
                    for (bd0 bd0Var5 = bd0Var4; bd0Var5 != null && bd0Var5.r; bd0Var5 = bd0Var5.j) {
                        if ((bd0Var5.g & 262144) != 0) {
                            jm jmVar = bd0Var5;
                            ?? r6 = 0;
                            while (jmVar != 0) {
                                if (jmVar instanceof w21) {
                                    w21 w21Var = (w21) jmVar;
                                    if (str.equals(w21Var.z())) {
                                        v21Var = (v21) gvVar.e(w21Var);
                                    } else {
                                        v21Var = v21.e;
                                    }
                                    if (v21Var != v21.g) {
                                        if (v21Var == v21.f) {
                                            break;
                                        }
                                    } else {
                                        return;
                                    }
                                } else if ((jmVar.g & 262144) != 0 && (jmVar instanceof jm)) {
                                    bd0 bd0Var6 = jmVar.t;
                                    int i2 = 0;
                                    jmVar = jmVar;
                                    r6 = r6;
                                    while (bd0Var6 != null) {
                                        if ((bd0Var6.g & 262144) != 0) {
                                            i2++;
                                            r6 = r6;
                                            if (i2 == 1) {
                                                jmVar = bd0Var6;
                                            } else {
                                                if (r6 == 0) {
                                                    r6 = new ef0(new bd0[16]);
                                                }
                                                if (jmVar != 0) {
                                                    r6.b(jmVar);
                                                    jmVar = 0;
                                                }
                                                r6.b(bd0Var6);
                                            }
                                        }
                                        bd0Var6 = bd0Var6.j;
                                        jmVar = jmVar;
                                        r6 = r6;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                jmVar = k81.h(r6);
                            }
                        }
                    }
                }
                k81.f(ef0Var, bd0Var4);
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [w21, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v0, types: [gv] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    public static final void M(w21 w21Var, gv gvVar) {
        v21 v21Var;
        if (!((bd0) w21Var).e.r) {
            q00.b("visitSubtreeIf called on an unattached node");
        }
        ef0 ef0Var = new ef0(new bd0[16]);
        bd0 bd0Var = ((bd0) w21Var).e;
        bd0 bd0Var2 = bd0Var.j;
        if (bd0Var2 == null) {
            k81.f(ef0Var, bd0Var);
        } else {
            ef0Var.b(bd0Var2);
        }
        while (true) {
            int i = ef0Var.g;
            if (i != 0) {
                bd0 bd0Var3 = (bd0) ef0Var.k(i - 1);
                if ((bd0Var3.h & 262144) != 0) {
                    for (bd0 bd0Var4 = bd0Var3; bd0Var4 != null && bd0Var4.r; bd0Var4 = bd0Var4.j) {
                        if ((bd0Var4.g & 262144) != 0) {
                            jm jmVar = bd0Var4;
                            ?? r7 = 0;
                            while (jmVar != 0) {
                                if (jmVar instanceof w21) {
                                    w21 w21Var2 = (w21) jmVar;
                                    if (o20.e(w21Var.z(), w21Var2.z()) && w21Var.getClass() == w21Var2.getClass()) {
                                        v21Var = (v21) gvVar.e(w21Var2);
                                    } else {
                                        v21Var = v21.e;
                                    }
                                    if (v21Var != v21.g) {
                                        if (v21Var == v21.f) {
                                            break;
                                        }
                                    } else {
                                        return;
                                    }
                                } else if ((jmVar.g & 262144) != 0 && (jmVar instanceof jm)) {
                                    bd0 bd0Var5 = jmVar.t;
                                    int i2 = 0;
                                    jmVar = jmVar;
                                    r7 = r7;
                                    while (bd0Var5 != null) {
                                        if ((bd0Var5.g & 262144) != 0) {
                                            i2++;
                                            r7 = r7;
                                            if (i2 == 1) {
                                                jmVar = bd0Var5;
                                            } else {
                                                if (r7 == 0) {
                                                    r7 = new ef0(new bd0[16]);
                                                }
                                                if (jmVar != 0) {
                                                    r7.b(jmVar);
                                                    jmVar = 0;
                                                }
                                                r7.b(bd0Var5);
                                            }
                                        }
                                        bd0Var5 = bd0Var5.j;
                                        jmVar = jmVar;
                                        r7 = r7;
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                jmVar = k81.h(r7);
                            }
                        }
                    }
                }
                k81.f(ef0Var, bd0Var3);
            } else {
                return;
            }
        }
    }

    public static final double N(long j) {
        return ((j >>> 11) * 2048.0d) + (j & 2047);
    }

    public static final int O(int i) {
        int i2 = 306783378 & i;
        int i3 = 613566756 & i;
        return (i & (-920350135)) | (i3 >> 1) | i2 | ((i2 << 1) & i3);
    }

    public static final void P(b7 b7Var, d7 d7Var) {
        d7Var.f.setValue(b7Var.e.getValue());
        i7 i7Var = d7Var.g;
        i7 i7Var2 = b7Var.f;
        int b = i7Var.b();
        for (int i = 0; i < b; i++) {
            i7Var.e(i7Var2.a(i), i);
        }
        d7Var.i = b7Var.h;
        d7Var.h = b7Var.g;
        d7Var.j = ((Boolean) b7Var.i.getValue()).booleanValue();
    }

    public static final void a(bw bwVar, int i) {
        boolean z;
        long f;
        bwVar.W(-565590000);
        int i2 = 0;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            if (!n20.D(bwVar)) {
                f = f31.f(4294967295L);
            } else {
                f = f31.f(4279374354L);
            }
            f31.b(null, jc0.C(-1376044379, new pw0(i2, f), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 15, false);
        }
    }

    public static final int b(ob0 ob0Var, ry ryVar) {
        long y0;
        ob0 s0 = ob0Var.s0();
        if (s0 == null) {
            q00.b("Child of " + ob0Var + " cannot be null when calculating alignment line");
        }
        if (ob0Var.w0().r().containsKey(ryVar)) {
            Integer num = (Integer) ob0Var.w0().r().get(ryVar);
            if (num != null) {
                return num.intValue();
            }
        } else {
            int r0 = s0.r0(ryVar);
            if (r0 != Integer.MIN_VALUE) {
                s0.n = true;
                ob0Var.o = true;
                ob0Var.D0();
                s0.n = false;
                ob0Var.o = false;
                if (ryVar instanceof ry) {
                    y0 = s0.y0() & 4294967295L;
                } else {
                    y0 = s0.y0() >> 32;
                }
                return r0 + ((int) y0);
            }
        }
        return Integer.MIN_VALUE;
    }

    public static final boolean c(float f) {
        if (!Float.isNaN(f) && Math.abs(f) >= 0.5f) {
            return false;
        }
        return true;
    }

    public static final String d(Object[] objArr, int i, int i2, y yVar) {
        StringBuilder sb = new StringBuilder((i2 * 3) + 2);
        sb.append("[");
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            Object obj = objArr[i + i3];
            if (obj == yVar) {
                sb.append("(this Collection)");
            } else {
                sb.append(obj);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static final void e(u41 u41Var, um0 um0Var, long j) {
        fm fmVar = (fm) u41Var.a;
        fmVar.getClass();
        x41 x41Var = fmVar.b;
        x41 x41Var2 = fmVar.a;
        boolean l = g30.l(um0Var);
        long j2 = um0Var.b;
        if (l) {
            bl[] blVarArr = x41Var2.d;
            Arrays.fill(blVarArr, 0, blVarArr.length, (Object) null);
            x41Var2.e = 0;
            bl[] blVarArr2 = x41Var.d;
            Arrays.fill(blVarArr2, 0, blVarArr2.length, (Object) null);
            x41Var.e = 0;
            fmVar.c = 0L;
        }
        if (!g30.n(um0Var)) {
            List list = um0Var.m;
            if (list == null) {
                list = er.e;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ly lyVar = (ly) list.get(i);
                fmVar.a(lyVar.a, ch0.g(lyVar.e, j));
            }
            fmVar.a(j2, ch0.g(um0Var.n, j));
        }
        if (g30.n(um0Var) && j2 - fmVar.c > 40) {
            bl[] blVarArr3 = x41Var2.d;
            Arrays.fill(blVarArr3, 0, blVarArr3.length, (Object) null);
            x41Var2.e = 0;
            bl[] blVarArr4 = x41Var.d;
            Arrays.fill(blVarArr4, 0, blVarArr4.length, (Object) null);
            x41Var.e = 0;
            fmVar.c = 0L;
        }
        fmVar.c = j2;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:3|(8:5|6|7|(3:(1:(1:11)(2:46|47))(1:48)|12|13)(8:49|(10:59|60|61|62|63|64|65|66|(1:68)|25)(7:51|52|53|54|15|16|(6:18|19|20|21|22|23)(2:40|41))|58|31|(1:33)|34|(1:38)|39)|14|15|16|(0)(0)))|78|6|7|(0)(0)|14|15|16|(0)(0)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x011c, code lost:
    
        if (j(r3, r5, r8) == r12) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0125, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0126, code lost:
    
        r2 = r1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00ea A[Catch: CancellationException -> 0x0125, TRY_LEAVE, TryCatch #6 {CancellationException -> 0x0125, blocks: (B:16:0x00d5, B:18:0x00ea), top: B:15:0x00d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
    /* JADX WARN: Type inference failed for: r1v5, types: [ep0, java.lang.Object] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x011c -> B:14:0x0091). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object f(defpackage.d7 r23, defpackage.z6 r24, long r25, final defpackage.gv r27, defpackage.ij r28) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d20.f(d7, z6, long, gv, ij):java.lang.Object");
    }

    public static final Object g(d7 d7Var, Float f, ds dsVar, gv gvVar, sz0 sz0Var) {
        Object f2 = f(d7Var, new p01(dsVar, d7Var.e, d7Var.f.getValue(), f, d7Var.g), d7Var.h, gvVar, sz0Var);
        if (f2 == ik.e) {
            return f2;
        }
        return x31.a;
    }

    public static float h(float[] fArr) {
        if (fArr.length < 6) {
            return 0.0f;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        float f4 = fArr[3];
        float f5 = fArr[4];
        float f6 = fArr[5];
        float f7 = (((((f3 * f6) + ((f2 * f5) + (f * f4))) - (f4 * f5)) - (f2 * f3)) - (f * f6)) * 0.5f;
        if (f7 < 0.0f) {
            return -f7;
        }
        return f7;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0076 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean i(defpackage.pt r7, defpackage.oj r8) {
        /*
            ot r0 = r7.I0()
            int r0 = r0.ordinal()
            if (r0 == 0) goto L81
            r1 = 3
            r2 = 0
            r3 = 2
            r4 = 1
            if (r0 == r4) goto L35
            if (r0 == r3) goto L81
            if (r0 != r1) goto L31
            boolean r0 = B(r7, r8)
            if (r0 != 0) goto L77
            mt r0 = r7.F0()
            boolean r0 = r0.a
            if (r0 == 0) goto L2d
            java.lang.Object r7 = r8.e(r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            goto L2e
        L2d:
            r7 = r2
        L2e:
            if (r7 == 0) goto L76
            goto L77
        L31:
            defpackage.v7.k()
            return r2
        L35:
            pt r0 = defpackage.n20.v(r7)
            java.lang.String r5 = "ActiveParent must have a focusedChild"
            if (r0 == 0) goto L7d
            ot r6 = r0.I0()
            int r6 = r6.ordinal()
            if (r6 == 0) goto L78
            if (r6 == r4) goto L55
            if (r6 == r3) goto L78
            if (r6 == r1) goto L51
            defpackage.v7.k()
            return r2
        L51:
            defpackage.v7.o(r5)
            return r2
        L55:
            boolean r1 = i(r0, r8)
            if (r1 != 0) goto L77
            boolean r7 = r(r7, r0, r3, r8)
            if (r7 != 0) goto L77
            mt r7 = r0.F0()
            boolean r7 = r7.a
            if (r7 == 0) goto L76
            java.lang.Object r7 = r8.e(r0)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L76
            goto L77
        L76:
            return r2
        L77:
            return r4
        L78:
            boolean r7 = r(r7, r0, r3, r8)
            return r7
        L7d:
            defpackage.v7.o(r5)
            return r2
        L81:
            boolean r7 = B(r7, r8)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d20.i(pt, oj):boolean");
    }

    public static final Object j(z6 z6Var, gv gvVar, rz0 rz0Var) {
        yj yjVar = rz0Var.f;
        if (z6Var.a()) {
            yjVar.getClass();
            if (yjVar.j(x1.K) == null) {
                yjVar.getClass();
                return o30.r(yjVar).d(gvVar, rz0Var);
            }
            v7.d();
            return null;
        }
        lw lwVar = new lw(gvVar, 2);
        yjVar.getClass();
        return o30.r(yjVar).d(lwVar, rz0Var);
    }

    public static s51 k(Class cls) {
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(null);
            if (Modifier.isPublic(declaredConstructor.getModifiers())) {
                try {
                    Object newInstance = declaredConstructor.newInstance(null);
                    newInstance.getClass();
                    return (s51) newInstance;
                } catch (IllegalAccessException e) {
                    v7.j("Cannot create an instance of ", cls, e);
                    return null;
                } catch (InstantiationException e2) {
                    v7.j("Cannot create an instance of ", cls, e2);
                    return null;
                }
            }
            throw new RuntimeException("Cannot create an instance of " + cls);
        } catch (NoSuchMethodException e3) {
            v7.j("Cannot create an instance of ", cls, e3);
            return null;
        }
    }

    public static final void l(b7 b7Var, long j, float f, z6 z6Var, d7 d7Var, gv gvVar) {
        long j2;
        if (f == 0.0f) {
            j2 = z6Var.c();
        } else {
            j2 = ((float) (j - b7Var.c)) / f;
        }
        b7Var.g = j;
        b7Var.e.setValue(z6Var.b(j2));
        b7Var.f = z6Var.f(j2);
        if (z6Var.g(j2)) {
            b7Var.h = b7Var.g;
            b7Var.i.setValue(Boolean.FALSE);
        }
        P(b7Var, d7Var);
        gvVar.e(b7Var);
    }

    public static final float m(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            f += fArr[i] * fArr2[i];
        }
        return f;
    }

    public static final float n(float f) {
        float intBitsToFloat = Float.intBitsToFloat(((int) ((Float.floatToRawIntBits(f) & 8589934591L) / 3)) + 709952852);
        float f2 = intBitsToFloat - ((intBitsToFloat - (f / (intBitsToFloat * intBitsToFloat))) * 0.33333334f);
        return f2 - ((f2 - (f / (f2 * f2))) * 0.33333334f);
    }

    public static final int o(int i, c70 c70Var, Object obj) {
        int c;
        if (obj != null && c70Var.c() != 0 && ((i >= c70Var.c() || !obj.equals(c70Var.d(i))) && (c = c70Var.d.c(obj)) != -1)) {
            return c;
        }
        return i;
    }

    public static final w21 p(jm jmVar, Object obj) {
        lg0 lg0Var;
        if (!jmVar.e.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var = jmVar.e.i;
        z40 E = k81.E(jmVar);
        while (E != null) {
            if ((E.H.f.h & 262144) != 0) {
                while (bd0Var != null) {
                    if ((bd0Var.g & 262144) != 0) {
                        bd0 bd0Var2 = bd0Var;
                        ef0 ef0Var = null;
                        while (bd0Var2 != null) {
                            if (bd0Var2 instanceof w21) {
                                w21 w21Var = (w21) bd0Var2;
                                if (obj.equals(w21Var.z())) {
                                    return w21Var;
                                }
                            }
                            if ((bd0Var2.g & 262144) != 0 && (bd0Var2 instanceof jm)) {
                                int i = 0;
                                for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                    if ((bd0Var3.g & 262144) != 0) {
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
        return null;
    }

    public static final boolean q(pt ptVar, oj ojVar) {
        int ordinal = ptVar.I0().ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal == 3) {
                        if (ptVar.F0().a) {
                            return ((Boolean) ojVar.e(ptVar)).booleanValue();
                        }
                        return C(ptVar, ojVar);
                    }
                    v7.k();
                    return false;
                }
            } else {
                pt v = n20.v(ptVar);
                if (v != null) {
                    if (!q(v, ojVar) && !r(ptVar, v, 1, ojVar)) {
                        return false;
                    }
                    return true;
                }
                v7.o("ActiveParent must have a focusedChild");
                return false;
            }
        }
        return C(ptVar, ojVar);
    }

    public static final boolean r(pt ptVar, pt ptVar2, int i, oj ojVar) {
        if (G(ptVar, ptVar2, i, ojVar)) {
            return true;
        }
        Boolean bool = (Boolean) o20.B(ptVar, i, new ph0(((lt) ((b4) k81.F(ptVar)).getFocusOwner()).f(), ptVar, ptVar2, i, ojVar, 0));
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static final Rect s(TextPaint textPaint, CharSequence charSequence, int i, int i2) {
        int i3 = i;
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            if (spanned.nextSpanTransition(i3 - 1, i2, MetricAffectingSpan.class) != i2) {
                Rect rect = new Rect();
                Rect rect2 = new Rect();
                TextPaint textPaint2 = new TextPaint();
                while (i3 < i2) {
                    int nextSpanTransition = spanned.nextSpanTransition(i3, i2, MetricAffectingSpan.class);
                    MetricAffectingSpan[] metricAffectingSpanArr = (MetricAffectingSpan[]) spanned.getSpans(i3, nextSpanTransition, MetricAffectingSpan.class);
                    textPaint2.set(textPaint);
                    for (MetricAffectingSpan metricAffectingSpan : metricAffectingSpanArr) {
                        if (spanned.getSpanStart(metricAffectingSpan) != spanned.getSpanEnd(metricAffectingSpan)) {
                            metricAffectingSpan.updateMeasureState(textPaint2);
                        }
                    }
                    if (Build.VERSION.SDK_INT >= 29) {
                        textPaint2.getTextBounds(charSequence, i3, nextSpanTransition, rect2);
                    } else {
                        textPaint2.getTextBounds(charSequence.toString(), i3, nextSpanTransition, rect2);
                    }
                    rect.right = rect2.width() + rect.right;
                    rect.top = Math.min(rect.top, rect2.top);
                    rect.bottom = Math.max(rect.bottom, rect2.bottom);
                    i3 = nextSpanTransition;
                }
                return rect;
            }
        }
        Rect rect3 = new Rect();
        if (Build.VERSION.SDK_INT >= 29) {
            textPaint.getTextBounds(charSequence, i3, i2, rect3);
            return rect3;
        }
        textPaint.getTextBounds(charSequence.toString(), i3, i2, rect3);
        return rect3;
    }

    public static final float t(yj yjVar) {
        float f;
        id0 id0Var = (id0) yjVar.j(x1.Q);
        if (id0Var != null) {
            f = id0Var.t();
        } else {
            f = 1.0f;
        }
        if (f >= 0.0f) {
            return f;
        }
        en0.b("negative scale factor");
        return f;
    }

    public static final Object u(nu0 nu0Var, av0 av0Var) {
        Object g = nu0Var.e.g(av0Var);
        if (g == null) {
            return null;
        }
        return g;
    }

    public static final long v(int i) {
        return A(4294967296L, i);
    }

    public static int w(int i) {
        if (i != 1) {
            if (i == 2) {
                return 1;
            }
            if (i == 4) {
                return 2;
            }
            if (i != 8) {
                if (i == 16) {
                    return 4;
                }
                if (i != 32) {
                    if (i != 64) {
                        if (i != 128) {
                            if (i == 256) {
                                return 8;
                            }
                            if (i == 512) {
                                return 9;
                            }
                            throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + i);
                        }
                        return 7;
                    }
                    return 6;
                }
                return 5;
            }
            return 3;
        }
        return 0;
    }

    public static final void x(np npVar, float f, float f2, boolean z, boolean z2) {
        float[] fArr;
        h6 r;
        RenderEffect createRuntimeShaderEffect;
        RenderEffect createChainEffect;
        npVar.getClass();
        if (y20.n()) {
            float f3 = 0.0f;
            if (f > 0.0f && f2 > 0.0f) {
                float f4 = npVar.i;
                if (f4 > 0.0f) {
                    float f5 = f4 - f;
                    if (f5 < 0.0f) {
                        f5 = 0.0f;
                    }
                    npVar.i = f5;
                }
                zv0 zv0Var = (zv0) npVar.l.t.a.a();
                if (zv0Var instanceof lr0) {
                    kr0 a = ((lr0) zv0Var).a(npVar.g, npVar.h, npVar);
                    fArr = new float[]{a.a, a.b, a.c, a.d};
                } else {
                    fArr = null;
                }
                if (fArr != null) {
                    wb0 wb0Var = npVar.k;
                    if (!z2) {
                        r = wb0Var.r("Refraction", "\nuniform shader content;\n\nuniform float2 size;\nuniform float2 offset;\nuniform float4 cornerRadii;\nuniform float refractionHeight;\nuniform float refractionAmount;\nuniform float depthEffect;\n\n\nfloat radiusAt(float2 coord, float4 radii) {\n    if (coord.x >= 0.0) {\n        if (coord.y <= 0.0) return radii.y;\n        else return radii.z;\n    } else {\n        if (coord.y <= 0.0) return radii.x;\n        else return radii.w;\n    }\n}\n\nfloat sdRoundedRect(float2 coord, float2 halfSize, float radius) {\n    float2 cornerCoord = abs(coord) - (halfSize - float2(radius));\n    float outside = length(max(cornerCoord, 0.0)) - radius;\n    float inside = min(max(cornerCoord.x, cornerCoord.y), 0.0);\n    return outside + inside;\n}\n\nfloat2 gradSdRoundedRect(float2 coord, float2 halfSize, float radius) {\n    float2 cornerCoord = abs(coord) - (halfSize - float2(radius));\n    if (cornerCoord.x >= 0.0 || cornerCoord.y >= 0.0) {\n        return sign(coord) * normalize(max(cornerCoord, 0.0));\n    } else {\n        float gradX = step(cornerCoord.y, cornerCoord.x);\n        return sign(coord) * float2(gradX, 1.0 - gradX);\n    }\n}\n\nfloat circleMap(float x) {\n    return 1.0 - sqrt(1.0 - x * x);\n}\n\nhalf4 main(float2 coord) {\n    float2 halfSize = size * 0.5;\n    float2 centeredCoord = (coord + offset) - halfSize;\n    float radius = radiusAt(coord, cornerRadii);\n    \n    float sd = sdRoundedRect(centeredCoord, halfSize, radius);\n    if (-sd >= refractionHeight) {\n        return content.eval(coord);\n    }\n    sd = min(sd, 0.0);\n    \n    float d = circleMap(1.0 - -sd / refractionHeight) * refractionAmount;\n    float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));\n    float2 grad = normalize(gradSdRoundedRect(centeredCoord, halfSize, gradRadius) + depthEffect * normalize(centeredCoord));\n    \n    float2 refractedCoord = coord + d * grad;\n    return content.eval(refractedCoord);\n}");
                    } else {
                        r = wb0Var.r("RefractionWithDispersion", "\nuniform shader content;\n\nuniform float2 size;\nuniform float2 offset;\nuniform float4 cornerRadii;\nuniform float refractionHeight;\nuniform float refractionAmount;\nuniform float depthEffect;\nuniform float chromaticAberration;\n\n\nfloat radiusAt(float2 coord, float4 radii) {\n    if (coord.x >= 0.0) {\n        if (coord.y <= 0.0) return radii.y;\n        else return radii.z;\n    } else {\n        if (coord.y <= 0.0) return radii.x;\n        else return radii.w;\n    }\n}\n\nfloat sdRoundedRect(float2 coord, float2 halfSize, float radius) {\n    float2 cornerCoord = abs(coord) - (halfSize - float2(radius));\n    float outside = length(max(cornerCoord, 0.0)) - radius;\n    float inside = min(max(cornerCoord.x, cornerCoord.y), 0.0);\n    return outside + inside;\n}\n\nfloat2 gradSdRoundedRect(float2 coord, float2 halfSize, float radius) {\n    float2 cornerCoord = abs(coord) - (halfSize - float2(radius));\n    if (cornerCoord.x >= 0.0 || cornerCoord.y >= 0.0) {\n        return sign(coord) * normalize(max(cornerCoord, 0.0));\n    } else {\n        float gradX = step(cornerCoord.y, cornerCoord.x);\n        return sign(coord) * float2(gradX, 1.0 - gradX);\n    }\n}\n\nfloat circleMap(float x) {\n    return 1.0 - sqrt(1.0 - x * x);\n}\n\nhalf4 main(float2 coord) {\n    float2 halfSize = size * 0.5;\n    float2 centeredCoord = (coord + offset) - halfSize;\n    float radius = radiusAt(coord, cornerRadii);\n    \n    float sd = sdRoundedRect(centeredCoord, halfSize, radius);\n    if (-sd >= refractionHeight) {\n        return content.eval(coord);\n    }\n    sd = min(sd, 0.0);\n    \n    float d = circleMap(1.0 - -sd / refractionHeight) * refractionAmount;\n    float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));\n    float2 grad = normalize(gradSdRoundedRect(centeredCoord, halfSize, gradRadius) + depthEffect * normalize(centeredCoord));\n    \n    float2 refractedCoord = coord + d * grad;\n    float dispersionIntensity = chromaticAberration * ((centeredCoord.x * centeredCoord.y) / (halfSize.x * halfSize.y));\n    float2 dispersedCoord = d * grad * dispersionIntensity;\n    \n    half4 color = half4(0.0);\n    \n    half4 red = content.eval(refractedCoord + dispersedCoord);\n    color.r += red.r / 3.5;\n    color.a += red.a / 7.0;\n    \n    half4 orange = content.eval(refractedCoord + dispersedCoord * (2.0 / 3.0));\n    color.r += orange.r / 3.5;\n    color.g += orange.g / 7.0;\n    color.a += orange.a / 7.0;\n    \n    half4 yellow = content.eval(refractedCoord + dispersedCoord * (1.0 / 3.0));\n    color.r += yellow.r / 3.5;\n    color.g += yellow.g / 3.5;\n    color.a += yellow.a / 7.0;\n    \n    half4 green = content.eval(refractedCoord);\n    color.g += green.g / 3.5;\n    color.a += green.a / 7.0;\n    \n    half4 cyan = content.eval(refractedCoord - dispersedCoord * (1.0 / 3.0));\n    color.g += cyan.g / 3.5;\n    color.b += cyan.b / 3.0;\n    color.a += cyan.a / 7.0;\n    \n    half4 blue = content.eval(refractedCoord - dispersedCoord * (2.0 / 3.0));\n    color.b += blue.b / 3.0;\n    color.a += blue.a / 7.0;\n    \n    half4 purple = content.eval(refractedCoord - dispersedCoord);\n    color.r += purple.r / 7.0;\n    color.b += purple.b / 3.0;\n    color.a += purple.a / 7.0;\n    \n    return color;\n}");
                    }
                    r.a.setFloatUniform("size", Float.intBitsToFloat((int) (npVar.g >> 32)), Float.intBitsToFloat((int) (npVar.g & 4294967295L)));
                    float f6 = -npVar.i;
                    r.a.setFloatUniform("offset", f6, f6);
                    r.a.setFloatUniform("cornerRadii", fArr);
                    r.a.setFloatUniform("refractionHeight", f);
                    r.a.setFloatUniform("refractionAmount", -f2);
                    if (z) {
                        f3 = 1.0f;
                    }
                    r.a.setFloatUniform("depthEffect", f3);
                    if (z2) {
                        r.a.setFloatUniform("chromaticAberration", 1.0f);
                    }
                    createRuntimeShaderEffect = RenderEffect.createRuntimeShaderEffect(r.a, "content");
                    createRuntimeShaderEffect.getClass();
                    e6 e6Var = new e6(createRuntimeShaderEffect);
                    if (Build.VERSION.SDK_INT >= 31) {
                        gh ghVar = npVar.j;
                        if (ghVar != null) {
                            createChainEffect = RenderEffect.createChainEffect(e6Var.c(), ghVar.c());
                            createChainEffect.getClass();
                            e6Var = new e6(createChainEffect);
                        }
                        npVar.j = e6Var;
                        return;
                    }
                    return;
                }
                throw new UnsupportedOperationException("Only RoundedRectangularShape or CornerBasedShape is supported in lens effects.");
            }
        }
    }

    public static /* synthetic */ void y(np npVar, float f, float f2, int i) {
        boolean z;
        boolean z2 = true;
        if ((i & 4) != 0) {
            z = false;
        } else {
            z = true;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        x(npVar, f, f2, z, z2);
    }

    public static final float z(float f, float f2, float f3) {
        return (f3 * f2) + ((1.0f - f3) * f);
    }
}
