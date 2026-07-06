package defpackage;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ik0 extends oy0 implements Parcelable, gx0 {
    public static final Parcelable.Creator<ik0> CREATOR = new Object();
    public final ix0 f;
    public hx0 g;

    public ik0(Object obj, ix0 ix0Var) {
        this.f = ix0Var;
        ww0 j = cx0.j();
        hx0 hx0Var = new hx0(j.g(), obj);
        if (!(j instanceof ax)) {
            hx0Var.b = new hx0(1L, obj);
        }
        this.g = hx0Var;
    }

    @Override // defpackage.ny0
    public final py0 a() {
        return this.g;
    }

    @Override // defpackage.oy0, defpackage.ny0
    public final py0 b(py0 py0Var, py0 py0Var2, py0 py0Var3) {
        if (this.f.b(((hx0) py0Var2).c, ((hx0) py0Var3).c)) {
            return py0Var2;
        }
        return null;
    }

    @Override // defpackage.ny0
    public final void c(py0 py0Var) {
        py0Var.getClass();
        this.g = (hx0) py0Var;
    }

    @Override // defpackage.gx0
    public final ix0 d() {
        return this.f;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        return ((hx0) cx0.u(this.g, this)).c;
    }

    @Override // defpackage.af0
    public final void setValue(Object obj) {
        ww0 j;
        hx0 hx0Var = (hx0) cx0.h(this.g);
        if (!this.f.b(hx0Var.c, obj)) {
            hx0 hx0Var2 = this.g;
            synchronized (cx0.c) {
                j = cx0.j();
                ((hx0) cx0.p(hx0Var2, this, j, hx0Var)).c = obj;
            }
            cx0.o(j, this);
        }
    }

    public final String toString() {
        return "MutableState(value=" + ((hx0) cx0.h(this.g)).c + ")@" + hashCode();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int i2;
        parcel.writeValue(getValue());
        x1 x1Var = x1.S;
        ix0 ix0Var = this.f;
        if (o20.e(ix0Var, x1Var)) {
            i2 = 0;
        } else if (o20.e(ix0Var, dt0.g)) {
            i2 = 1;
        } else if (o20.e(ix0Var, x1.V)) {
            i2 = 2;
        } else {
            v7.o("Only known types of MutableState's SnapshotMutationPolicy are supported");
            return;
        }
        parcel.writeInt(i2);
    }
}
