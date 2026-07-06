package defpackage;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fk0 extends oy0 implements Parcelable, gx0, hy0, af0 {
    public static final Parcelable.Creator<fk0> CREATOR = new v1(5);
    public ex0 f;

    public fk0(int i) {
        ww0 j = cx0.j();
        ex0 ex0Var = new ex0(i, j.g());
        if (!(j instanceof ax)) {
            ex0Var.b = new ex0(i, 1L);
        }
        this.f = ex0Var;
    }

    @Override // defpackage.ny0
    public final py0 a() {
        return this.f;
    }

    @Override // defpackage.oy0, defpackage.ny0
    public final py0 b(py0 py0Var, py0 py0Var2, py0 py0Var3) {
        if (((ex0) py0Var2).c == ((ex0) py0Var3).c) {
            return py0Var2;
        }
        return null;
    }

    @Override // defpackage.ny0
    public final void c(py0 py0Var) {
        py0Var.getClass();
        this.f = (ex0) py0Var;
    }

    @Override // defpackage.gx0
    public final ix0 d() {
        return dt0.g;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int g() {
        return ((ex0) cx0.u(this.f, this)).c;
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        return Integer.valueOf(g());
    }

    public final void h(int i) {
        ww0 j;
        ex0 ex0Var = (ex0) cx0.h(this.f);
        if (ex0Var.c != i) {
            ex0 ex0Var2 = this.f;
            synchronized (cx0.c) {
                j = cx0.j();
                ((ex0) cx0.p(ex0Var2, this, j, ex0Var)).c = i;
            }
            cx0.o(j, this);
        }
    }

    @Override // defpackage.af0
    public final void setValue(Object obj) {
        h(((Number) obj).intValue());
    }

    public final String toString() {
        return "MutableIntState(value=" + ((ex0) cx0.h(this.f)).c + ")@" + hashCode();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(g());
    }
}
