package defpackage;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.net.Uri;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class dt0 implements ix0, u71, tm0, vt, jm0, s11, y7, w7, xj, px0 {
    public static final dt0 f = new dt0(0);
    public static final dt0 g = new dt0(1);
    public static final dt0 h = new dt0(2);
    public static final dt0 i = new dt0(3);
    public static final v71 j = new Object();
    public final /* synthetic */ int e;

    public dt0() {
        this.e = 17;
        new vb0(16);
        long[] jArr = zs0.a;
        new ve0();
    }

    @Override // defpackage.y7, defpackage.w7
    public /* synthetic */ float a() {
        switch (this.e) {
            case 14:
                return 0.0f;
            case 15:
                return 0.0f;
            default:
                return 0.0f;
        }
    }

    @Override // defpackage.ix0
    public boolean b(Object obj, Object obj2) {
        return o20.e(obj, obj2);
    }

    @Override // defpackage.u71
    public q71 c(ContextWrapper contextWrapper, nm nmVar) {
        sa saVar;
        nmVar.getClass();
        Context context = contextWrapper;
        while (true) {
            if (context instanceof ContextWrapper) {
                if ((context instanceof Activity) || (context instanceof InputMethodService)) {
                    break;
                }
                ContextWrapper contextWrapper2 = (ContextWrapper) context;
                if (contextWrapper2.getBaseContext() == null) {
                    break;
                }
                context = contextWrapper2.getBaseContext();
                context.getClass();
            } else {
                context = contextWrapper;
                break;
            }
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            sa.a.getClass();
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 30) {
                saVar = ta.e;
            } else if (i2 >= 29) {
                saVar = x1.w;
            } else if (i2 >= 28) {
                saVar = x1.v;
            } else if (i2 >= 24) {
                saVar = x1.u;
            } else {
                saVar = x1.t;
            }
            return new q71(new qa(saVar.e(activity)), nmVar.f(activity));
        }
        if (!(context instanceof InputMethodService) && !(context instanceof Application)) {
            v7.m("Must provide a UiContext or Application Context");
            return null;
        }
        Object systemService = contextWrapper.getSystemService("window");
        systemService.getClass();
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        defaultDisplay.getClass();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return new q71(new Rect(0, 0, point.x, point.y), nmVar.f(contextWrapper));
    }

    @Override // defpackage.w7
    public void d(mm mmVar, int i2, int[] iArr, m40 m40Var, int[] iArr2) {
        int i3 = 0;
        if (m40Var == m40.e) {
            int length = iArr.length;
            int i4 = 0;
            int i5 = 0;
            while (i3 < length) {
                int i6 = iArr[i3];
                iArr2[i4] = i5;
                i5 += i6;
                i3++;
                i4++;
            }
            return;
        }
        int length2 = iArr.length;
        int i7 = 0;
        while (i3 < length2) {
            i7 += iArr[i3];
            i3++;
        }
        int i8 = i2 - i7;
        int length3 = iArr.length;
        while (true) {
            length3--;
            if (-1 < length3) {
                int i9 = iArr[length3];
                iArr2[length3] = i8;
                i8 += i9;
            } else {
                return;
            }
        }
    }

    @Override // defpackage.y7
    public void e(int i2, rc0 rc0Var, int[] iArr, int[] iArr2) {
        int i3 = 0;
        switch (this.e) {
            case 14:
                int i4 = 0;
                for (int i5 : iArr) {
                    i4 += i5;
                }
                int i6 = i2 - i4;
                int length = iArr.length;
                int i7 = 0;
                while (i3 < length) {
                    int i8 = iArr[i3];
                    iArr2[i7] = i6;
                    i6 += i8;
                    i3++;
                    i7++;
                }
                return;
            default:
                int length2 = iArr.length;
                int i9 = 0;
                int i10 = 0;
                while (i3 < length2) {
                    int i11 = iArr[i3];
                    iArr2[i9] = i10;
                    i10 += i11;
                    i3++;
                    i9++;
                }
                return;
        }
    }

    public long f(long j2, long j3) {
        switch (this.e) {
            case 21:
                float max = Math.max(Float.intBitsToFloat((int) (j3 >> 32)) / Float.intBitsToFloat((int) (j2 >> 32)), Float.intBitsToFloat((int) (j3 & 4294967295L)) / Float.intBitsToFloat((int) (j2 & 4294967295L)));
                long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max) & 4294967295L);
                int i2 = ys0.a;
                return floatToRawIntBits;
            case 22:
                float g2 = k81.g(j2, j3);
                long floatToRawIntBits2 = (Float.floatToRawIntBits(g2) << 32) | (Float.floatToRawIntBits(g2) & 4294967295L);
                int i3 = ys0.a;
                return floatToRawIntBits2;
            default:
                if (Float.intBitsToFloat((int) (j2 >> 32)) <= Float.intBitsToFloat((int) (j3 >> 32)) && Float.intBitsToFloat((int) (j2 & 4294967295L)) <= Float.intBitsToFloat((int) (j3 & 4294967295L))) {
                    long floatToRawIntBits3 = (Float.floatToRawIntBits(1.0f) << 32) | (Float.floatToRawIntBits(1.0f) & 4294967295L);
                    int i4 = ys0.a;
                    return floatToRawIntBits3;
                }
                float g3 = k81.g(j2, j3);
                long floatToRawIntBits4 = (Float.floatToRawIntBits(g3) << 32) | (Float.floatToRawIntBits(g3) & 4294967295L);
                int i5 = ys0.a;
                return floatToRawIntBits4;
        }
    }

    @Override // defpackage.jm0
    public ua0 g() {
        return new ua0(jc0.v(new ta0(Locale.getDefault())));
    }

    public Signature[] h(PackageManager packageManager, String str) {
        return packageManager.getPackageInfo(str, 64).signatures;
    }

    public Uri i(Intent intent, int i2) {
        List arrayList;
        if (i2 != -1) {
            intent = null;
        }
        if (intent == null) {
            return null;
        }
        Uri data = intent.getData();
        if (data == null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Uri data2 = intent.getData();
            if (data2 != null) {
                linkedHashSet.add(data2);
            }
            ClipData clipData = intent.getClipData();
            if (clipData == null && linkedHashSet.isEmpty()) {
                arrayList = er.e;
            } else {
                if (clipData != null) {
                    int itemCount = clipData.getItemCount();
                    for (int i3 = 0; i3 < itemCount; i3++) {
                        Uri uri = clipData.getItemAt(i3).getUri();
                        if (uri != null) {
                            linkedHashSet.add(uri);
                        }
                    }
                }
                arrayList = new ArrayList(linkedHashSet);
            }
            return (Uri) me.T(arrayList);
        }
        return data;
    }

    public String toString() {
        switch (this.e) {
            case 1:
                return "StructuralEqualityPolicy";
            case 14:
                return "Arrangement#Bottom";
            case 15:
                return "Arrangement#Start";
            case 16:
                return "Arrangement#Top";
            case 19:
                return "Empty";
            case 20:
                return "CompositionErrorContext";
            default:
                return super.toString();
        }
    }

    public /* synthetic */ dt0(int i2) {
        this.e = i2;
    }

    public dt0(b4 b4Var) {
        this.e = 7;
        sm0.a.getClass();
    }
}
