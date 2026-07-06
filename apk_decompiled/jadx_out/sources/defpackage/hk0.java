package defpackage;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hk0 implements Parcelable.ClassLoaderCreator {
    public static ik0 a(Parcel parcel, ClassLoader classLoader) {
        ix0 ix0Var;
        if (classLoader == null) {
            classLoader = hk0.class.getClassLoader();
        }
        Object readValue = parcel.readValue(classLoader);
        int readInt = parcel.readInt();
        if (readInt != 0) {
            if (readInt != 1) {
                if (readInt == 2) {
                    ix0Var = x1.V;
                } else {
                    throw new IllegalStateException("Unsupported MutableState policy " + readInt + " was restored");
                }
            } else {
                ix0Var = dt0.g;
            }
        } else {
            ix0Var = x1.S;
        }
        return new ik0(readValue, ix0Var);
    }

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        return a(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        return new ik0[i];
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return a(parcel, classLoader);
    }
}
