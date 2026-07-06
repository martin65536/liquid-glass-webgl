package defpackage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Region;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i3 implements uc {
    public Canvas a = j3.a;
    public Rect b;
    public Rect c;

    @Override // defpackage.uc
    public final void a(float f, float f2) {
        this.a.scale(f, f2);
    }

    @Override // defpackage.uc
    public final void b(float f, long j, r5 r5Var) {
        this.a.drawCircle(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), f, o4.H(r5Var));
    }

    @Override // defpackage.uc
    public final void c(o5 o5Var, long j, long j2, long j3, r5 r5Var) {
        if (this.b == null) {
            this.b = new Rect();
            this.c = new Rect();
        }
        Canvas canvas = this.a;
        Bitmap j4 = f31.j(o5Var);
        Rect rect = this.b;
        rect.getClass();
        int i = (int) (j >> 32);
        rect.left = i;
        int i2 = (int) (j & 4294967295L);
        rect.top = i2;
        rect.right = i + ((int) (j2 >> 32));
        rect.bottom = i2 + ((int) (j2 & 4294967295L));
        Rect rect2 = this.c;
        rect2.getClass();
        rect2.left = 0;
        rect2.top = 0;
        rect2.right = (int) (j3 >> 32);
        rect2.bottom = (int) (j3 & 4294967295L);
        canvas.drawBitmap(j4, rect, rect2, o4.H(r5Var));
    }

    @Override // defpackage.uc
    public final void d(float f, float f2) {
        this.a.translate(f, f2);
    }

    @Override // defpackage.uc
    public final void e(y5 y5Var, r5 r5Var) {
        Canvas canvas = this.a;
        if (y5Var instanceof y5) {
            canvas.drawPath(y5Var.a, o4.H(r5Var));
            return;
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
    }

    @Override // defpackage.uc
    public final void f() {
        this.a.restore();
    }

    @Override // defpackage.uc
    public final void g(float f, float f2, float f3, float f4, float f5, float f6, r5 r5Var) {
        this.a.drawRoundRect(f, f2, f3, f4, f5, f6, o4.H(r5Var));
    }

    @Override // defpackage.uc
    public final void h() {
        this.a.save();
    }

    @Override // defpackage.uc
    public final void i(wo0 wo0Var, r5 r5Var) {
        m(wo0Var.a, wo0Var.b, wo0Var.c, wo0Var.d, r5Var);
    }

    @Override // defpackage.uc
    public final void j() {
        o20.n(this.a, false);
    }

    @Override // defpackage.uc
    public final void k(wo0 wo0Var) {
        n(wo0Var.a, wo0Var.b, wo0Var.c, wo0Var.d);
    }

    @Override // defpackage.uc
    public final void l(wo0 wo0Var, r5 r5Var) {
        this.a.saveLayer(wo0Var.a, wo0Var.b, wo0Var.c, wo0Var.d, o4.H(r5Var), 31);
    }

    @Override // defpackage.uc
    public final void m(float f, float f2, float f3, float f4, r5 r5Var) {
        this.a.drawRect(f, f2, f3, f4, o4.H(r5Var));
    }

    @Override // defpackage.uc
    public final void n(float f, float f2, float f3, float f4) {
        this.a.clipRect(f, f2, f3, f4, Region.Op.INTERSECT);
    }

    @Override // defpackage.uc
    public final void o(float[] fArr) {
        if (!t20.A(fArr)) {
            Matrix matrix = new Matrix();
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float f4 = fArr[3];
            float f5 = fArr[4];
            float f6 = fArr[5];
            float f7 = fArr[6];
            float f8 = fArr[7];
            float f9 = fArr[8];
            float f10 = fArr[12];
            float f11 = fArr[13];
            float f12 = fArr[15];
            fArr[0] = f;
            fArr[1] = f5;
            fArr[2] = f10;
            fArr[3] = f2;
            fArr[4] = f6;
            fArr[5] = f11;
            fArr[6] = f4;
            fArr[7] = f8;
            fArr[8] = f12;
            matrix.setValues(fArr);
            fArr[0] = f;
            fArr[1] = f2;
            fArr[2] = f3;
            fArr[3] = f4;
            fArr[4] = f5;
            fArr[5] = f6;
            fArr[6] = f7;
            fArr[7] = f8;
            fArr[8] = f9;
            this.a.concat(matrix);
        }
    }

    @Override // defpackage.uc
    public final void p() {
        o20.n(this.a, true);
    }

    @Override // defpackage.uc
    public final void q(y5 y5Var) {
        Canvas canvas = this.a;
        if (y5Var instanceof y5) {
            canvas.clipPath(y5Var.a, Region.Op.INTERSECT);
            return;
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
    }
}
