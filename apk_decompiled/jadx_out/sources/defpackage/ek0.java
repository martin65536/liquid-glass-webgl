package defpackage;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ek0 extends oy0 implements Parcelable, gx0, hy0, af0 {
    public static final Parcelable.Creator<ek0> CREATOR = new v1(4);
    public dx0 f;

    public ek0(float f) {
        ww0 j = cx0.j();
        dx0 dx0Var = new dx0(j.g(), f);
        if (!(j instanceof ax)) {
            dx0Var.b = new dx0(1L, f);
        }
        this.f = dx0Var;
    }

    @Override // defpackage.ny0
    public final py0 a() {
        return this.f;
    }

    @Override // defpackage.oy0, defpackage.ny0
    public final py0 b(py0 py0Var, py0 py0Var2, py0 py0Var3) {
        if (((dx0) py0Var2).c == ((dx0) py0Var3).c) {
            return py0Var2;
        }
        return null;
    }

    @Override // defpackage.ny0
    public final void c(py0 py0Var) {
        py0Var.getClass();
        this.f = (dx0) py0Var;
    }

    @Override // defpackage.gx0
    public final ix0 d() {
        return dt0.g;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final float g() {
        return ((dx0) cx0.u(this.f, this)).c;
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        return Float.valueOf(g());
    }

    public final void h(float f) {
        ww0 j;
        dx0 dx0Var = (dx0) cx0.h(this.f);
        if (dx0Var.c == f) {
            return;
        }
        dx0 dx0Var2 = this.f;
        synchronized (cx0.c) {
            j = cx0.j();
            ((dx0) cx0.p(dx0Var2, this, j, dx0Var)).c = f;
        }
        cx0.o(j, this);
    }

    @Override // defpackage.af0
    public final void setValue(Object obj) {
        h(((Number) obj).floatValue());
    }

    public final String toString() {
        return "MutableFloatState(value=" + ((dx0) cx0.h(this.f)).c + ")@" + hashCode();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(g());
    }
}
