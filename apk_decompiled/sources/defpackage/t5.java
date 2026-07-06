package defpackage;

import android.graphics.Canvas;
import android.text.TextUtils;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t5 {
    public final x5 a;
    public final int b;
    public final long c;
    public final f11 d;
    public final CharSequence e;
    public final List f;

    /* JADX WARN: Removed duplicated region for block: B:102:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x026e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public t5(defpackage.x5 r21, int r22, int r23, long r24) {
        /*
            Method dump skipped, instructions count: 850
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.t5.<init>(x5, int, int, long):void");
    }

    public final f11 a(int i, int i2, TextUtils.TruncateAt truncateAt, int i3, int i4, int i5, int i6, int i7, CharSequence charSequence) {
        boolean z;
        mm0 mm0Var;
        float c = c();
        x5 x5Var = this.a;
        j6 j6Var = x5Var.g;
        int i8 = x5Var.l;
        p40 p40Var = x5Var.i;
        r11 r11Var = x5Var.b;
        u5 u5Var = v5.a;
        nm0 nm0Var = r11Var.c;
        if (nm0Var != null && (mm0Var = nm0Var.a) != null) {
            z = mm0Var.a;
        } else {
            z = false;
        }
        return new f11(charSequence, c, j6Var, i, truncateAt, i8, z, i3, i5, i6, i7, i4, i2, p40Var);
    }

    public final float b() {
        return this.d.a();
    }

    public final float c() {
        return si.h(this.c);
    }

    public final void d(uc ucVar) {
        Canvas a = j3.a(ucVar);
        f11 f11Var = this.d;
        if (f11Var.d) {
            a.save();
            a.clipRect(0.0f, 0.0f, c(), b());
        }
        int i = f11Var.g;
        if (a.getClipBounds(f11Var.o)) {
            if (i != 0) {
                a.translate(0.0f, i);
            }
            ThreadLocal threadLocal = i11.a;
            Object obj = threadLocal.get();
            if (obj == null) {
                obj = new Canvas();
                threadLocal.set(obj);
            }
            v01 v01Var = (v01) obj;
            v01Var.a = a;
            try {
                f11Var.e.draw(v01Var);
                if (i != 0) {
                    a.translate(0.0f, (-1.0f) * i);
                }
            } finally {
                v01Var.a = null;
            }
        }
        if (f11Var.d) {
            a.restore();
        }
    }
}
