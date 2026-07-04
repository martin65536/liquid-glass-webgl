package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Parcel;
import android.os.Parcelable;
import defpackage.a51;
import defpackage.v7;
import defpackage.z41;
import java.nio.charset.Charset;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class IconCompatParcelizer {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.core.graphics.drawable.IconCompat, java.lang.Object] */
    public static IconCompat read(z41 z41Var) {
        int readInt;
        ?? obj = new Object();
        obj.a = -1;
        obj.c = null;
        obj.d = null;
        obj.e = 0;
        obj.f = 0;
        obj.g = null;
        obj.h = IconCompat.k;
        obj.i = null;
        if (!z41Var.e(1)) {
            readInt = -1;
        } else {
            readInt = ((a51) z41Var).e.readInt();
        }
        obj.a = readInt;
        byte[] bArr = obj.c;
        if (z41Var.e(2)) {
            Parcel parcel = ((a51) z41Var).e;
            int readInt2 = parcel.readInt();
            if (readInt2 < 0) {
                bArr = null;
            } else {
                byte[] bArr2 = new byte[readInt2];
                parcel.readByteArray(bArr2);
                bArr = bArr2;
            }
        }
        obj.c = bArr;
        obj.d = z41Var.f(obj.d, 3);
        int i = obj.e;
        if (z41Var.e(4)) {
            i = ((a51) z41Var).e.readInt();
        }
        obj.e = i;
        int i2 = obj.f;
        if (z41Var.e(5)) {
            i2 = ((a51) z41Var).e.readInt();
        }
        obj.f = i2;
        obj.g = (ColorStateList) z41Var.f(obj.g, 6);
        String str = obj.i;
        if (z41Var.e(7)) {
            str = ((a51) z41Var).e.readString();
        }
        obj.i = str;
        String str2 = obj.j;
        if (z41Var.e(8)) {
            str2 = ((a51) z41Var).e.readString();
        }
        obj.j = str2;
        obj.h = PorterDuff.Mode.valueOf(obj.i);
        switch (obj.a) {
            case -1:
                Parcelable parcelable = obj.d;
                if (parcelable != null) {
                    obj.b = parcelable;
                    return obj;
                }
                v7.m("Invalid icon");
                return null;
            case 0:
            default:
                return obj;
            case 1:
            case 5:
                Parcelable parcelable2 = obj.d;
                if (parcelable2 != null) {
                    obj.b = parcelable2;
                    return obj;
                }
                byte[] bArr3 = obj.c;
                obj.b = bArr3;
                obj.a = 3;
                obj.e = 0;
                obj.f = bArr3.length;
                return obj;
            case 2:
            case 4:
            case 6:
                String str3 = new String(obj.c, Charset.forName("UTF-16"));
                obj.b = str3;
                if (obj.a == 2 && obj.j == null) {
                    obj.j = str3.split(":", -1)[0];
                }
                return obj;
            case 3:
                obj.b = obj.c;
                return obj;
        }
    }

    public static void write(IconCompat iconCompat, z41 z41Var) {
        z41Var.getClass();
        iconCompat.i = iconCompat.h.name();
        switch (iconCompat.a) {
            case -1:
                iconCompat.d = (Parcelable) iconCompat.b;
                break;
            case 1:
            case 5:
                iconCompat.d = (Parcelable) iconCompat.b;
                break;
            case 2:
                iconCompat.c = ((String) iconCompat.b).getBytes(Charset.forName("UTF-16"));
                break;
            case 3:
                iconCompat.c = (byte[]) iconCompat.b;
                break;
            case 4:
            case 6:
                iconCompat.c = iconCompat.b.toString().getBytes(Charset.forName("UTF-16"));
                break;
        }
        int i = iconCompat.a;
        if (-1 != i) {
            z41Var.h(1);
            ((a51) z41Var).e.writeInt(i);
        }
        byte[] bArr = iconCompat.c;
        if (bArr != null) {
            z41Var.h(2);
            Parcel parcel = ((a51) z41Var).e;
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(bArr);
        }
        Parcelable parcelable = iconCompat.d;
        if (parcelable != null) {
            z41Var.h(3);
            ((a51) z41Var).e.writeParcelable(parcelable, 0);
        }
        int i2 = iconCompat.e;
        if (i2 != 0) {
            z41Var.h(4);
            ((a51) z41Var).e.writeInt(i2);
        }
        int i3 = iconCompat.f;
        if (i3 != 0) {
            z41Var.h(5);
            ((a51) z41Var).e.writeInt(i3);
        }
        ColorStateList colorStateList = iconCompat.g;
        if (colorStateList != null) {
            z41Var.h(6);
            ((a51) z41Var).e.writeParcelable(colorStateList, 0);
        }
        String str = iconCompat.i;
        if (str != null) {
            z41Var.h(7);
            ((a51) z41Var).e.writeString(str);
        }
        String str2 = iconCompat.j;
        if (str2 != null) {
            z41Var.h(8);
            ((a51) z41Var).e.writeString(str2);
        }
    }
}
