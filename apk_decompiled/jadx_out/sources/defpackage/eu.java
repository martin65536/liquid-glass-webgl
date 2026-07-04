package defpackage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class eu {
    public static final vb0 a = new vb0(2);
    public static final b6 b = new b6(1);

    /* JADX WARN: Type inference failed for: r9v2, types: [pu, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v3, types: [pu, java.lang.Object] */
    public static pu a(Context context, List list) {
        Typeface typeface;
        n30.f("FontProvider.getFontFamilyResult");
        try {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                fu fuVar = (fu) list.get(i);
                if (Build.VERSION.SDK_INT >= 31) {
                    String str = fuVar.e;
                    y20 y20Var = g31.a;
                    if (str != null && !str.isEmpty()) {
                        typeface = Typeface.create(str, 0);
                        Typeface create = Typeface.create(Typeface.DEFAULT, 0);
                        if (typeface != null && !typeface.equals(create)) {
                            if (typeface != null && g31.a(typeface) != null) {
                                arrayList.add(new qu[]{new qu(str, fuVar.f)});
                            }
                        }
                    }
                    typeface = null;
                    if (typeface != null) {
                        arrayList.add(new qu[]{new qu(str, fuVar.f)});
                    }
                }
                ProviderInfo b2 = b(context.getPackageManager(), fuVar, context.getResources());
                if (b2 == null) {
                    ?? obj = new Object();
                    obj.a = 1;
                    obj.b = Collections.singletonList(null);
                    return obj;
                }
                arrayList.add(c(context, fuVar, b2.authority));
            }
            ?? obj2 = new Object();
            obj2.a = 0;
            obj2.b = arrayList;
            return obj2;
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [du, java.lang.Object] */
    public static ProviderInfo b(PackageManager packageManager, fu fuVar, Resources resources) {
        b6 b6Var = b;
        vb0 vb0Var = a;
        n30.f("FontProvider.getProvider");
        try {
            List list = fuVar.d;
            String str = fuVar.a;
            String str2 = fuVar.b;
            if (list == null) {
                list = jc0.B(resources, 0);
            }
            ?? obj = new Object();
            obj.a = str;
            obj.b = str2;
            obj.c = list;
            ProviderInfo providerInfo = (ProviderInfo) vb0Var.a(obj);
            if (providerInfo != null) {
                return providerInfo;
            }
            ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(str, 0);
            if (resolveContentProvider != null) {
                if (resolveContentProvider.packageName.equals(str2)) {
                    Signature[] signatureArr = packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures;
                    ArrayList arrayList = new ArrayList();
                    for (Signature signature : signatureArr) {
                        arrayList.add(signature.toByteArray());
                    }
                    Collections.sort(arrayList, b6Var);
                    for (int i = 0; i < list.size(); i++) {
                        ArrayList arrayList2 = new ArrayList((Collection) list.get(i));
                        Collections.sort(arrayList2, b6Var);
                        if (arrayList.size() == arrayList2.size()) {
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                if (!Arrays.equals((byte[]) arrayList.get(i2), (byte[]) arrayList2.get(i2))) {
                                    break;
                                }
                            }
                            vb0Var.b(obj, resolveContentProvider);
                            return resolveContentProvider;
                        }
                    }
                    Trace.endSection();
                    return null;
                }
                throw new PackageManager.NameNotFoundException("Found content provider " + str + ", but package was not " + str2);
            }
            throw new PackageManager.NameNotFoundException("No package found for authority: " + str);
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:11:0x0070. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a6 A[Catch: all -> 0x00e5, TryCatch #1 {all -> 0x00e5, blocks: (B:16:0x00a1, B:18:0x00a6, B:20:0x00ac, B:21:0x00d5, B:25:0x00de, B:27:0x00ec, B:29:0x00f7, B:32:0x010d, B:35:0x0119, B:38:0x0124, B:42:0x0102), top: B:15:0x00a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0134 A[Catch: all -> 0x0157, TRY_ENTER, TryCatch #3 {all -> 0x0157, blocks: (B:3:0x000d, B:5:0x0042, B:48:0x0134, B:49:0x0137, B:56:0x0150, B:57:0x0153, B:58:0x0156, B:78:0x0048), top: B:2:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0150 A[Catch: all -> 0x0157, TRY_ENTER, TryCatch #3 {all -> 0x0157, blocks: (B:3:0x000d, B:5:0x0042, B:48:0x0134, B:49:0x0137, B:56:0x0150, B:57:0x0153, B:58:0x0156, B:78:0x0048), top: B:2:0x000d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static defpackage.qu[] c(android.content.Context r22, defpackage.fu r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eu.c(android.content.Context, fu, java.lang.String):qu[]");
    }
}
