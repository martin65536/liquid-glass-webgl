package defpackage;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x1 implements sa, xj, nm, p60, nq0, ix0, kz0 {
    public final /* synthetic */ int e;
    public static final x1 f = new x1(0);
    public static final ba g = new ba(-1.0f, -1.0f);
    public static final ba h = new ba(0.0f, -1.0f);
    public static final ba i = new ba(1.0f, -1.0f);
    public static final ba j = new ba(-1.0f, 0.0f);
    public static final ba k = new ba(0.0f, 0.0f);
    public static final ba l = new ba(1.0f, 0.0f);
    public static final ba m = new ba(-1.0f, 1.0f);
    public static final ba n = new ba(0.0f, 1.0f);
    public static final ba o = new ba(1.0f, 1.0f);
    public static final aa p = new aa(-1.0f);
    public static final aa q = new aa(0.0f);
    public static final z9 r = new z9(-1.0f);
    public static final z9 s = new z9(0.0f);
    public static final x1 t = new x1(2);
    public static final x1 u = new x1(3);
    public static final x1 v = new x1(4);
    public static final x1 w = new x1(5);
    public static final v7 x = new v7(3);
    public static final u4 y = new u4(1);
    public static final u4 z = new u4(2);
    public static final /* synthetic */ x1 A = new x1(8);
    public static final /* synthetic */ x1 B = new x1(9);
    public static final x1 C = new x1(10);
    public static final x1 D = new x1(11);
    public static final x1 E = new x1(12);
    public static final x1 F = new x1(13);
    public static final x1 G = new x1(14);
    public static final wo0 H = new wo0(Float.NaN, Float.NaN, Float.NaN, Float.NaN);
    public static final x1 I = new x1(16);
    public static final /* synthetic */ x1 J = new x1(17);
    public static final /* synthetic */ x1 K = new x1(18);
    public static final /* synthetic */ x1 L = new x1(19);
    public static final x1 M = new x1(20);
    public static final x1 N = new x1(21);
    public static final x1 O = new x1(22);
    public static final /* synthetic */ x1 P = new x1(23);
    public static final /* synthetic */ x1 Q = new x1(24);
    public static final x1 R = new x1(25);
    public static final x1 S = new x1(26);
    public static final x1 T = new x1(27);
    public static final /* synthetic */ x1 U = new x1(28);
    public static final x1 V = new x1(29);

    public /* synthetic */ x1(int i2) {
        this.e = i2;
    }

    @Override // defpackage.ix0
    public boolean b(Object obj, Object obj2) {
        switch (this.e) {
            case 26:
                return false;
            default:
                if (obj == obj2) {
                    return true;
                }
                return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object c(defpackage.hx r11, defpackage.ij r12) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x1.c(hx, ij):java.lang.Object");
    }

    @Override // defpackage.kz0
    public void d(jz0 jz0Var) {
        jz0Var.clear();
    }

    @Override // defpackage.sa
    public Rect e(Activity activity) {
        int i2;
        boolean isInMultiWindowMode;
        boolean isInMultiWindowMode2;
        boolean isInMultiWindowMode3;
        int safeInsetLeft;
        int safeInsetRight;
        int safeInsetTop;
        int safeInsetBottom;
        int safeInsetBottom2;
        int safeInsetRight2;
        int i3;
        boolean isInMultiWindowMode4;
        int i4 = this.e;
        ra raVar = sa.a;
        DisplayCutout displayCutout = null;
        int i5 = 0;
        switch (i4) {
            case 2:
                Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
                defaultDisplay.getClass();
                Point point = new Point();
                defaultDisplay.getRealSize(point);
                Rect rect = new Rect();
                int i6 = point.x;
                if (i6 != 0 && (i2 = point.y) != 0) {
                    rect.right = i6;
                    rect.bottom = i2;
                } else {
                    defaultDisplay.getRectSize(rect);
                }
                return rect;
            case 3:
                Rect rect2 = new Rect();
                Display defaultDisplay2 = activity.getWindowManager().getDefaultDisplay();
                defaultDisplay2.getRectSize(rect2);
                isInMultiWindowMode = activity.isInMultiWindowMode();
                if (!isInMultiWindowMode) {
                    Point point2 = new Point();
                    defaultDisplay2.getRealSize(point2);
                    Resources resources = activity.getResources();
                    int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (identifier > 0) {
                        i5 = resources.getDimensionPixelSize(identifier);
                    }
                    int i7 = rect2.bottom + i5;
                    if (i7 == point2.y) {
                        rect2.bottom = i7;
                    } else {
                        int i8 = rect2.right + i5;
                        if (i8 == point2.x) {
                            rect2.right = i8;
                        }
                    }
                }
                return rect2;
            case 4:
                Rect rect3 = new Rect();
                Configuration configuration = activity.getResources().getConfiguration();
                try {
                    Field declaredField = Configuration.class.getDeclaredField("windowConfiguration");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(configuration);
                    isInMultiWindowMode4 = activity.isInMultiWindowMode();
                    if (isInMultiWindowMode4) {
                        Object invoke = obj.getClass().getDeclaredMethod("getBounds", null).invoke(obj, null);
                        invoke.getClass();
                        rect3.set((Rect) invoke);
                    } else {
                        Object invoke2 = obj.getClass().getDeclaredMethod("getAppBounds", null).invoke(obj, null);
                        invoke2.getClass();
                        rect3.set((Rect) invoke2);
                    }
                } catch (Exception e) {
                    if (!(e instanceof NoSuchFieldException) && !(e instanceof NoSuchMethodException) && !(e instanceof IllegalAccessException) && !(e instanceof InvocationTargetException)) {
                        throw e;
                    }
                    raVar.getClass();
                    Log.w(ra.b, e);
                    activity.getWindowManager().getDefaultDisplay().getRectSize(rect3);
                }
                Display defaultDisplay3 = activity.getWindowManager().getDefaultDisplay();
                Point point3 = new Point();
                defaultDisplay3.getRealSize(point3);
                isInMultiWindowMode2 = activity.isInMultiWindowMode();
                if (!isInMultiWindowMode2) {
                    Resources resources2 = activity.getResources();
                    int identifier2 = resources2.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (identifier2 > 0) {
                        i3 = resources2.getDimensionPixelSize(identifier2);
                    } else {
                        i3 = 0;
                    }
                    int i9 = rect3.bottom + i3;
                    if (i9 == point3.y) {
                        rect3.bottom = i9;
                    } else {
                        int i10 = rect3.right + i3;
                        if (i10 == point3.x) {
                            rect3.right = i10;
                        } else if (rect3.left == i3) {
                            rect3.left = 0;
                        }
                    }
                }
                if (rect3.width() < point3.x || rect3.height() < point3.y) {
                    isInMultiWindowMode3 = activity.isInMultiWindowMode();
                    if (!isInMultiWindowMode3) {
                        try {
                            Constructor<?> constructor = Class.forName("android.view.DisplayInfo").getConstructor(null);
                            constructor.setAccessible(true);
                            Object newInstance = constructor.newInstance(null);
                            Method declaredMethod = defaultDisplay3.getClass().getDeclaredMethod("getDisplayInfo", newInstance.getClass());
                            declaredMethod.setAccessible(true);
                            declaredMethod.invoke(defaultDisplay3, newInstance);
                            Field declaredField2 = newInstance.getClass().getDeclaredField("displayCutout");
                            declaredField2.setAccessible(true);
                            Object obj2 = declaredField2.get(newInstance);
                            if (y0.q(obj2)) {
                                displayCutout = y0.f(obj2);
                            }
                        } catch (Exception e2) {
                            if (!(e2 instanceof ClassNotFoundException) && !(e2 instanceof NoSuchMethodException) && !(e2 instanceof NoSuchFieldException) && !(e2 instanceof IllegalAccessException) && !(e2 instanceof InvocationTargetException) && !(e2 instanceof InstantiationException)) {
                                throw e2;
                            }
                            raVar.getClass();
                            Log.w(ra.b, e2);
                        }
                        if (displayCutout != null) {
                            int i11 = rect3.left;
                            safeInsetLeft = displayCutout.getSafeInsetLeft();
                            if (i11 == safeInsetLeft) {
                                rect3.left = 0;
                            }
                            int i12 = point3.x - rect3.right;
                            safeInsetRight = displayCutout.getSafeInsetRight();
                            if (i12 == safeInsetRight) {
                                int i13 = rect3.right;
                                safeInsetRight2 = displayCutout.getSafeInsetRight();
                                rect3.right = safeInsetRight2 + i13;
                            }
                            int i14 = rect3.top;
                            safeInsetTop = displayCutout.getSafeInsetTop();
                            if (i14 == safeInsetTop) {
                                rect3.top = 0;
                            }
                            int i15 = point3.y - rect3.bottom;
                            safeInsetBottom = displayCutout.getSafeInsetBottom();
                            if (i15 == safeInsetBottom) {
                                int i16 = rect3.bottom;
                                safeInsetBottom2 = displayCutout.getSafeInsetBottom();
                                rect3.bottom = safeInsetBottom2 + i16;
                            }
                        }
                    }
                }
                return rect3;
            default:
                Configuration configuration2 = activity.getResources().getConfiguration();
                try {
                    Field declaredField3 = Configuration.class.getDeclaredField("windowConfiguration");
                    declaredField3.setAccessible(true);
                    Object obj3 = declaredField3.get(configuration2);
                    Object invoke3 = obj3.getClass().getDeclaredMethod("getBounds", null).invoke(obj3, null);
                    invoke3.getClass();
                    return new Rect((Rect) invoke3);
                } catch (Exception e3) {
                    if (!(e3 instanceof NoSuchFieldException) && !(e3 instanceof NoSuchMethodException) && !(e3 instanceof IllegalAccessException) && !(e3 instanceof InvocationTargetException)) {
                        throw e3;
                    }
                    raVar.getClass();
                    Log.w(ra.b, e3);
                    return v.e(activity);
                }
        }
    }

    @Override // defpackage.nm
    public float f(ContextWrapper contextWrapper) {
        return contextWrapper.getResources().getDisplayMetrics().density;
    }

    @Override // defpackage.kz0
    public boolean i(Object obj, Object obj2) {
        return false;
    }

    public String toString() {
        switch (this.e) {
            case 26:
                return "NeverEqualPolicy";
            case 29:
                return "ReferentialEqualityPolicy";
            default:
                return super.toString();
        }
    }

    @Override // defpackage.p60
    public void a() {
    }

    @Override // defpackage.p60
    public void cancel() {
    }
}
