package defpackage;

import android.R;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e1 {
    public static final e1 c;
    public static final e1 d;
    public static final e1 e;
    public static final e1 f;
    public static final e1 g;
    public static final e1 h;
    public static final e1 i;
    public static final e1 j;
    public final Object a;
    public final int b;

    static {
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction2;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction3;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction4;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction5;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction6;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction7;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction8;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction9;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction10;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction11;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction12;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction13;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction14;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction15;
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction16;
        int i2;
        new e1(null, 1, null, null);
        new e1(null, 2, null, null);
        new e1(null, 4, null, null);
        new e1(null, 8, null, null);
        new e1(null, 16, null, null);
        new e1(null, 32, null, null);
        c = new e1(null, 64, null, null);
        d = new e1(null, 128, null, null);
        new e1(null, 256, null, n1.class);
        new e1(null, 512, null, n1.class);
        new e1(null, 1024, null, o1.class);
        new e1(null, 2048, null, o1.class);
        e = new e1(null, 4096, null, null);
        f = new e1(null, 8192, null, null);
        new e1(null, 16384, null, null);
        new e1(null, 32768, null, null);
        new e1(null, 65536, null, null);
        new e1(null, 131072, null, s1.class);
        new e1(null, 262144, null, null);
        new e1(null, 524288, null, null);
        new e1(null, 1048576, null, null);
        new e1(null, 2097152, null, t1.class);
        new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN, R.id.accessibilityActionShowOnScreen, null, null);
        new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION, R.id.accessibilityActionScrollToPosition, null, q1.class);
        g = new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP, R.id.accessibilityActionScrollUp, null, null);
        h = new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT, R.id.accessibilityActionScrollLeft, null, null);
        i = new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN, R.id.accessibilityActionScrollDown, null, null);
        j = new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT, R.id.accessibilityActionScrollRight, null, null);
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 29) {
            accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP;
        } else {
            accessibilityAction = null;
        }
        new e1(accessibilityAction, R.id.accessibilityActionPageUp, null, null);
        if (i3 >= 29) {
            accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN;
        } else {
            accessibilityAction2 = null;
        }
        new e1(accessibilityAction2, R.id.accessibilityActionPageDown, null, null);
        if (i3 >= 29) {
            accessibilityAction3 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT;
        } else {
            accessibilityAction3 = null;
        }
        new e1(accessibilityAction3, R.id.accessibilityActionPageLeft, null, null);
        if (i3 >= 29) {
            accessibilityAction4 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT;
        } else {
            accessibilityAction4 = null;
        }
        new e1(accessibilityAction4, R.id.accessibilityActionPageRight, null, null);
        new e1(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK, R.id.accessibilityActionContextClick, null, null);
        if (i3 >= 24) {
            accessibilityAction5 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
        } else {
            accessibilityAction5 = null;
        }
        new e1(accessibilityAction5, R.id.accessibilityActionSetProgress, null, r1.class);
        if (i3 >= 26) {
            accessibilityAction6 = AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW;
        } else {
            accessibilityAction6 = null;
        }
        new e1(accessibilityAction6, R.id.accessibilityActionMoveWindow, null, p1.class);
        if (i3 >= 28) {
            accessibilityAction7 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP;
        } else {
            accessibilityAction7 = null;
        }
        new e1(accessibilityAction7, R.id.accessibilityActionShowTooltip, null, null);
        if (i3 >= 28) {
            accessibilityAction8 = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
        } else {
            accessibilityAction8 = null;
        }
        new e1(accessibilityAction8, R.id.accessibilityActionHideTooltip, null, null);
        if (i3 >= 30) {
            accessibilityAction9 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PRESS_AND_HOLD;
        } else {
            accessibilityAction9 = null;
        }
        new e1(accessibilityAction9, R.id.accessibilityActionPressAndHold, null, null);
        if (i3 >= 30) {
            accessibilityAction10 = AccessibilityNodeInfo.AccessibilityAction.ACTION_IME_ENTER;
        } else {
            accessibilityAction10 = null;
        }
        new e1(accessibilityAction10, R.id.accessibilityActionImeEnter, null, null);
        if (i3 >= 32) {
            accessibilityAction11 = AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_START;
        } else {
            accessibilityAction11 = null;
        }
        new e1(accessibilityAction11, R.id.ALT, null, null);
        if (i3 >= 32) {
            accessibilityAction12 = AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP;
        } else {
            accessibilityAction12 = null;
        }
        new e1(accessibilityAction12, R.id.CTRL, null, null);
        if (i3 >= 32) {
            accessibilityAction13 = AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL;
        } else {
            accessibilityAction13 = null;
        }
        new e1(accessibilityAction13, R.id.FUNCTION, null, null);
        if (i3 >= 33) {
            accessibilityAction14 = d1.h();
        } else {
            accessibilityAction14 = null;
        }
        new e1(accessibilityAction14, R.id.KEYCODE_0, null, null);
        if (i3 >= 34) {
            accessibilityAction15 = r0.a();
        } else {
            accessibilityAction15 = null;
        }
        new e1(accessibilityAction15, R.id.KEYCODE_3D_MODE, null, null);
        int i4 = dc.a;
        if (i3 >= 36) {
            if (i3 >= 36) {
                i2 = Build.VERSION.SDK_INT_FULL;
            } else {
                i2 = i3 * 100000;
            }
            if (i2 >= 3600001) {
                accessibilityAction16 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_EXTENDED_SELECTION;
                new e1(accessibilityAction16, R.id.KEYCODE_4, null, null);
            }
        }
        accessibilityAction16 = null;
        new e1(accessibilityAction16, R.id.KEYCODE_4, null, null);
    }

    public e1(Object obj, int i2, CharSequence charSequence, Class cls) {
        this.b = i2;
        if (obj == null) {
            this.a = new AccessibilityNodeInfo.AccessibilityAction(i2, charSequence);
        } else {
            this.a = obj;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof e1)) {
            return false;
        }
        Object obj2 = ((e1) obj).a;
        Object obj3 = this.a;
        if (obj3 == null) {
            if (obj2 != null) {
                return false;
            }
            return true;
        }
        if (!obj3.equals(obj2)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        Object obj = this.a;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AccessibilityActionCompat: ");
        String c2 = k1.c(this.b);
        if (c2.equals("ACTION_UNKNOWN")) {
            Object obj = this.a;
            if (((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel() != null) {
                c2 = ((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel().toString();
            }
        }
        sb.append(c2);
        return sb.toString();
    }

    public e1(String str, int i2) {
        this(null, i2, str, null);
    }
}
