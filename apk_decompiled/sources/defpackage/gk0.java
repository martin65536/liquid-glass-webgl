package defpackage;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gk0 extends oy0 implements Parcelable, gx0, hy0, af0 {
    public static final Parcelable.Creator<gk0> CREATOR = new v1(6);
    public fx0 f;

    public gk0(long j) {
        ww0 j2 = cx0.j();
        fx0 fx0Var = new fx0(j2.g(), j);
        if (!(j2 instanceof ax)) {
            fx0Var.b = new fx0(1L, j);
        }
        this.f = fx0Var;
    }

    @Override // defpackage.ny0
    public final py0 a() {
        return this.f;
    }

    @Override // defpackage.oy0, defpackage.ny0
    public final py0 b(py0 py0Var, py0 py0Var2, py0 py0Var3) {
        if (((fx0) py0Var2).c == ((fx0) py0Var3).c) {
            return py0Var2;
        }
        return null;
    }

    @Override // defpackage.ny0
    public final void c(py0 py0Var) {
        py0Var.getClass();
        this.f = (fx0) py0Var;
    }

    @Override // defpackage.gx0
    public final ix0 d() {
        return dt0.g;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void g(long j) {
        ww0 j2;
        fx0 fx0Var = (fx0) cx0.h(this.f);
        if (fx0Var.c != j) {
            fx0 fx0Var2 = this.f;
            synchronized (cx0.c) {
                j2 = cx0.j();
                ((fx0) cx0.p(fx0Var2, this, j2, fx0Var)).c = j;
            }
            cx0.o(j2, this);
        }
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        return Long.valueOf(((fx0) cx0.u(this.f, this)).c);
    }

    @Override // defpackage.af0
    public final void setValue(Object obj) {
        g(((Number) obj).longValue());
    }

    public final String toString() {
        return "MutableLongState(value=" + ((fx0) cx0.h(this.f)).c + ")@" + hashCode();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(((fx0) cx0.u(this.f, this)).c);
    }
}
