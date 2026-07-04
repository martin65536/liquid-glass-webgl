package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.a51;
import defpackage.b51;
import defpackage.v1;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new v1(3);
    public final b51 e;

    public ParcelImpl(Parcel parcel) {
        this.e = new a51(parcel).g();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        new a51(parcel).i(this.e);
    }
}
