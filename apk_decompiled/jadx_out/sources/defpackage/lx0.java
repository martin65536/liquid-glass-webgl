package defpackage;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lx0 implements Parcelable.ClassLoaderCreator {
    public static mx0 a(Parcel parcel, ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = lx0.class.getClassLoader();
        }
        int readInt = parcel.readInt();
        if (readInt == 0) {
            return new mx0();
        }
        yl0 e = vw0.f.e();
        for (int i = 0; i < readInt; i++) {
            e.add(parcel.readValue(classLoader));
        }
        return new mx0(e.c());
    }

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        return a(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        return new mx0[i];
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return a(parcel, classLoader);
    }
}
