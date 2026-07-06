package androidx.core.app;

import android.app.PendingIntent;
import android.os.Parcel;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;
import defpackage.a51;
import defpackage.b51;
import defpackage.z41;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class RemoteActionCompatParcelizer {
    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.core.app.RemoteActionCompat, java.lang.Object] */
    public static RemoteActionCompat read(z41 z41Var) {
        ?? obj = new Object();
        b51 b51Var = obj.a;
        boolean z = true;
        if (z41Var.e(1)) {
            b51Var = z41Var.g();
        }
        obj.a = (IconCompat) b51Var;
        CharSequence charSequence = obj.b;
        if (z41Var.e(2)) {
            charSequence = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(((a51) z41Var).e);
        }
        obj.b = charSequence;
        CharSequence charSequence2 = obj.c;
        if (z41Var.e(3)) {
            charSequence2 = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(((a51) z41Var).e);
        }
        obj.c = charSequence2;
        obj.d = (PendingIntent) z41Var.f(obj.d, 4);
        boolean z2 = obj.e;
        if (z41Var.e(5)) {
            if (((a51) z41Var).e.readInt() != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
        }
        obj.e = z2;
        boolean z3 = obj.f;
        if (!z41Var.e(6)) {
            z = z3;
        } else if (((a51) z41Var).e.readInt() == 0) {
            z = false;
        }
        obj.f = z;
        return obj;
    }

    public static void write(RemoteActionCompat remoteActionCompat, z41 z41Var) {
        z41Var.getClass();
        IconCompat iconCompat = remoteActionCompat.a;
        z41Var.h(1);
        z41Var.i(iconCompat);
        CharSequence charSequence = remoteActionCompat.b;
        z41Var.h(2);
        Parcel parcel = ((a51) z41Var).e;
        TextUtils.writeToParcel(charSequence, parcel, 0);
        CharSequence charSequence2 = remoteActionCompat.c;
        z41Var.h(3);
        TextUtils.writeToParcel(charSequence2, parcel, 0);
        PendingIntent pendingIntent = remoteActionCompat.d;
        z41Var.h(4);
        parcel.writeParcelable(pendingIntent, 0);
        boolean z = remoteActionCompat.e;
        z41Var.h(5);
        parcel.writeInt(z ? 1 : 0);
        boolean z2 = remoteActionCompat.f;
        z41Var.h(6);
        parcel.writeInt(z2 ? 1 : 0);
    }
}
