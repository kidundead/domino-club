package br.ufpe.cin.gui.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.ufpe.cin.controller.ClientEngine;

@SuppressWarnings("serial")
public class RoomsPanel extends JPanel {

	private JButton btnEnterRoom;
	private JCheckBox cbOnlyNonEmpty;
	private final ClientEngine engine;
	private JLabel lblStatus;
	private ArrayList<ArrayList<String>> list;
	private Map<Integer, String> rooms;
	private DefaultTableModel tableModelRooms;
	private JTable tblRooms;
	private JTextField txtFilter;

	public RoomsPanel(ClientEngine _engine) {

		engine = _engine;
		list = null;
		rooms = new HashMap<Integer, String>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel roomsPanel = new JPanel(new BorderLayout(5, 5));
		roomsPanel.setFocusable(true);

		String tblProdutosPedidoCols[] = { "Sala", "Criador", "Jogadores" };
		tableModelRooms = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModelRooms.setColumnIdentifiers(tblProdutosPedidoCols);
		tblRooms = new JTable(tableModelRooms);
		tblRooms.setAutoCreateColumnsFromModel(true);
		tblRooms.setFocusable(true);
		tblRooms.getTableHeader().setBackground(Color.lightGray);
		tblRooms.setRowHeight(36);
		tblRooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRooms.getColumn("Jogadores").setMaxWidth(120);
		tblRooms.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnEnterRoom.doClick();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (tblRooms.getSelectedRow() > 0) {
						tblRooms.setRowSelectionInterval(
								tblRooms.getSelectedRow() - 1,
								tblRooms.getSelectedRow() - 1);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (tblRooms.getSelectedRow() < tblRooms.getRowCount() - 1) {
						tblRooms.setRowSelectionInterval(
								tblRooms.getSelectedRow() + 1,
								tblRooms.getSelectedRow() + 1);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		JPanel titlePanel = new JPanel(new BorderLayout(0, 0));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		JLabel lblTitle = new JLabel("Domino Club");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		titlePanel.add(lblTitle, BorderLayout.CENTER);
		GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.anchor = GridBagConstraints.NORTH;
		gbc_titlePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_titlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_titlePanel.gridx = 0;
		gbc_titlePanel.gridy = 0;
		add(titlePanel, gbc_titlePanel);

		JPanel filterPanel = new JPanel();
		filterPanel.setBorder(new EmptyBorder(0, 10, 0, 15));
		GridBagConstraints gbc_filterPanel = new GridBagConstraints();
		gbc_filterPanel.anchor = GridBagConstraints.EAST;
		gbc_filterPanel.insets = new Insets(0, 0, 5, 0);
		gbc_filterPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_filterPanel.gridx = 0;
		gbc_filterPanel.gridy = 1;
		add(filterPanel, gbc_filterPanel);
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));

		JLabel lblFilter = new JLabel("Filtro: ");
		filterPanel.add(lblFilter);

		txtFilter = new JTextField();
		filterPanel.add(txtFilter);
		txtFilter.setColumns(10);
		txtFilter.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				refreshTable();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		cbOnlyNonEmpty = new JCheckBox("Ocultar sala cheias");
		filterPanel.add(cbOnlyNonEmpty);
		cbOnlyNonEmpty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});

		roomsPanel.add(new JScrollPane(tblRooms), BorderLayout.CENTER);
		mainPanel.add(roomsPanel, BorderLayout.CENTER);

		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.fill = GridBagConstraints.BOTH;
		gbc_mainPanel.insets = new Insets(0, 0, 5, 0);
		gbc_mainPanel.gridx = 0;
		gbc_mainPanel.gridy = 2;
		add(mainPanel, gbc_mainPanel);

		JPanel btnsPanel = new JPanel();
		GridBagConstraints gbc_btnsPanel = new GridBagConstraints();
		gbc_btnsPanel.anchor = GridBagConstraints.EAST;
		gbc_btnsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_btnsPanel.fill = GridBagConstraints.VERTICAL;
		gbc_btnsPanel.gridx = 0;
		gbc_btnsPanel.gridy = 3;
		add(btnsPanel, gbc_btnsPanel);

		JButton btnCreateRoom = new JButton("Criar sala");
		btnCreateRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final CreateRoomDialog dialog = new CreateRoomDialog();
				dialog.btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!dialog.getRoomName().isEmpty()) {
							engine.createRoom(dialog.getRoomName());
							dialog.dispose();
						} else
							dialog.setStatus("Entre com um nome para a sala!");
					}
				});
			}
		});
		btnsPanel.add(btnCreateRoom);

		btnEnterRoom = new JButton("Entrar na sala");
		btnEnterRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = tblRooms.getSelectedRow();
				if (row >= 0) {
					if (Integer.parseInt(tblRooms.getValueAt(row, 2).toString()
							.trim().substring(0, 1)) < 4) {
						engine.enterRoom(rooms.get(row));
					} else {
						JOptionPane.showMessageDialog(null, "Sala cheia!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma sala!");
				}
			}
		});
		btnsPanel.add(btnEnterRoom);

		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		lblStatus = new JLabel("Bem-vindo ao Domino Club!");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(lblStatus);
		GridBagConstraints gbc_statusPanel = new GridBagConstraints();
		gbc_statusPanel.anchor = GridBagConstraints.NORTH;
		gbc_statusPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusPanel.gridx = 0;
		gbc_statusPanel.gridy = 4;
		add(statusPanel, gbc_statusPanel);

		tblRooms.grabFocus();
	}

	public void clearTable() {
		while (tableModelRooms.getRowCount() > 0)
			tableModelRooms.removeRow(0);
	}

	public void refreshTable() {
		clearTable();
		rooms.clear();

		int i = 0;
		for (ArrayList<String> l : list) {
			if (!txtFilter.getText().isEmpty()
					&& !l.get(1).toLowerCase()
							.contains(txtFilter.getText().toLowerCase())
					&& !l.get(2).toLowerCase()
							.contains(txtFilter.getText().toLowerCase()))
				continue;
			if (cbOnlyNonEmpty.isSelected() && Integer.parseInt(l.get(3)) == 4)
				continue;

			tableModelRooms.insertRow(tableModelRooms.getRowCount(),
					new Object[] { l.get(1), l.get(2), l.get(3) + " / 4" });
			rooms.put(i, l.get(0));
			i++;
		}

		if (tblRooms.getRowCount() > 0)
			tblRooms.setRowSelectionInterval(0, 0);
	}

	public void setRoomsList(ArrayList<ArrayList<String>> list) {
		this.list = list;
		refreshTable();
	}

	public void setStatus(String str) {
		lblStatus.setText(str);
	}
}
