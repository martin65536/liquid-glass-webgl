package defpackage;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.versionedparcelable.ParcelImpl;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v1 implements Parcelable.Creator {
    public final /* synthetic */ int a;

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        Intent intent;
        switch (this.a) {
            case 0:
                parcel.getClass();
                int readInt = parcel.readInt();
                if (parcel.readInt() == 0) {
                    intent = null;
                } else {
                    intent = (Intent) Intent.CREATOR.createFromParcel(parcel);
                }
                return new w1(intent, readInt);
            case 1:
                return new wl(parcel.readInt());
            case 2:
                parcel.getClass();
                return new f20(parcel);
            case 3:
                return new ParcelImpl(parcel);
            case 4:
                return new ek0(parcel.readFloat());
            case 5:
                return new fk0(parcel.readInt());
            default:
                return new gk0(parcel.readLong());
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        switch (this.a) {
            case 0:
                return new w1[i];
            case 1:
                return new wl[i];
            case 2:
                return new f20[i];
            case 3:
                return new ParcelImpl[i];
            case 4:
                return new ek0[i];
            case 5:
                return new fk0[i];
            default:
                return new gk0[i];
        }
    }
}
