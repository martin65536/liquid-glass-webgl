package defpackage;

import android.os.Build;
import android.view.ViewConfiguration;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ud0 extends ug0 {
    public final j2 f;
    public final zb g;
    public dy0 h;

    public ud0(hu0 hu0Var, j2 j2Var, fg fgVar, mm mmVar) {
        super(hu0Var, fgVar, mmVar);
        this.f = j2Var;
        this.g = f31.c(Integer.MAX_VALUE, 6, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x016b A[PHI: r12
      0x016b: PHI (r12v6 java.lang.Object) = (r12v4 java.lang.Object), (r12v7 java.lang.Object) binds: [B:38:0x0101, B:28:0x0169] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x016c A[PHI: r16
      0x016c: PHI (r16v1 x31) = (r16v0 x31), (r16v2 x31) binds: [B:36:0x00d7, B:28:0x0169] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0035  */
    /* JADX WARN: Type inference failed for: r1v3, types: [bp0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v12, types: [ep0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1, types: [ep0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object c(defpackage.ud0 r19, defpackage.hu0 r20, defpackage.qd0 r21, float r22, float r23, defpackage.jj r24) {
        /*
            Method dump skipped, instructions count: 365
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ud0.c(ud0, hu0, qd0, float, float, jj):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object d(defpackage.ud0 r11, defpackage.ep0 r12, defpackage.bp0 r13, defpackage.hu0 r14, defpackage.ep0 r15, long r16, defpackage.jj r18) {
        /*
            Method dump skipped, instructions count: 205
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ud0.d(ud0, ep0, bp0, hu0, ep0, long, jj):java.lang.Object");
    }

    public static qd0 g(zb zbVar) {
        qd0 qd0Var = null;
        mv0 y = g30.y(new su(new pd0(zbVar, 0), null));
        while (y.hasNext()) {
            qd0 qd0Var2 = (qd0) y.next();
            if (qd0Var != null) {
                qd0Var2 = qd0Var.a(qd0Var2);
            }
            qd0Var = qd0Var2;
        }
        return qd0Var;
    }

    public final float e(fu0 fu0Var, float f) {
        hu0 hu0Var = this.a;
        long h = hu0Var.h(hu0Var.d(f));
        hu0 hu0Var2 = fu0Var.a;
        return hu0Var.g(hu0Var.e(hu0Var2.c(hu0Var2.k, h, 1)));
    }

    public final boolean f(pm0 pm0Var) {
        float G;
        float G2;
        long j;
        mm mmVar = this.c;
        ViewConfiguration viewConfiguration = (ViewConfiguration) this.f.f;
        int i = Build.VERSION.SDK_INT;
        if (i > 26) {
            G = ye.f(viewConfiguration);
        } else {
            G = mmVar.G(64.0f);
        }
        float f = -G;
        if (i > 26) {
            G2 = ye.c(viewConfiguration);
        } else {
            G2 = mmVar.G(64.0f);
        }
        float f2 = -G2;
        List list = pm0Var.a;
        ch0 ch0Var = new ch0(0L);
        int size = list.size();
        boolean z = false;
        int i2 = 0;
        while (true) {
            j = ch0Var.a;
            if (i2 >= size) {
                break;
            }
            ch0Var = new ch0(ch0.g(j, ((um0) list.get(i2)).j));
            i2++;
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) * f2;
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) * f;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        hu0 hu0Var = this.a;
        float i3 = hu0Var.i(hu0Var.e(floatToRawIntBits));
        if (i3 != 0.0f) {
            au0 au0Var = hu0Var.a;
            if (i3 > 0.0f) {
                z = au0Var.c();
            } else {
                z = au0Var.a();
            }
        }
        if (z) {
            return !(this.g.q(new qd0(floatToRawIntBits, ((um0) me.S(pm0Var.a)).b, false)) instanceof nd);
        }
        return this.d;
    }
}
